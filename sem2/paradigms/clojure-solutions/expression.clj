(load-file "proto.clj")
(load-file "parser.clj")
(defn abstractOp [f d]
  (fn [& args]
    (cond (not (nil? args))
          (fn [variables]
            (apply f ((apply juxt args) variables)))
          :else (constantly d)))) -
(def add (abstractOp + 0.0))
(def subtract (abstractOp - 0.0))
(def negate (abstractOp (fn [a] (- a)) 0.0))

(def multiply (abstractOp * 1.0))
(def divide (abstractOp (fn ([a] (/ 1.0 a))
                          ([a, & b] (/ (double a) (reduce * b)))) 0.0))
(def constant constantly)
(defn variable [v] (fn [variables] (get variables v)))
(def exp (abstractOp #(Math/exp %) 1.0))
(def ln (abstractOp #(Math/log %) 1.0))
(def pow (abstractOp (fn [a, b] (Math/pow a b)) 1.0))
(def log (abstractOp (fn [a, b] (/ (Math/log (Math/abs b)) (Math/log (Math/abs a)))) 1.0))
(def FUNCTIONAL_OPERATORS {'variable variable
                           'constant constant
                           '/        divide,
                           '*        multiply
                           '-        subtract
                           '+        add
                           'exp      exp
                           'ln       ln
                           'pow      pow
                           'log      log
                           'negate   negate})
(def _name (field :name))
(def _args (field :args))
(def _function (field :function))
(def _diffFunction (field :diffFunction))
(def toString (method :toString))
(def evaluate (method :evaluate))
(def diff (method :diff))
(def toStringSuffix (method :toStringSuffix))
(def Operation {
                :diff           (fn [this, v] ((_diffFunction this) (_args this) (mapv #(diff % v) (_args this))))
                :toString       (fn [this] (str "(" (_name this) " " (clojure.string/join " " (map (fn [a] (toString a)) (_args this))) ")"))
                :toStringSuffix (fn [this] (str "(" (clojure.string/join " " (map (fn [a] (toStringSuffix a)) (_args this))) " " (_name this) ")"))
                :evaluate       (fn [this variables]
                                  (apply (_function this) (map (fn [a] (evaluate a variables)) (_args this))))})
(def OperationConstructor (constructor (fn [this, function, diffFunction, name, args]
                                         (assoc this :function function :diffFunction diffFunction :name name :args args)) Operation))

(defn d ([a] (/ 1.0 a))
  ([a, & b] (/ (double a) (reduce * b))))
(declare ZERO)
(declare ONE)
(def ConstantPrototype {:evaluate       (fn [this _] (_args this))
                        :toString       (fn [this] (str (_args this)))
                        :toStringSuffix (fn [this] (str (_args this)))
                        :diff           (fn [_ _] (identity ZERO))})
(def ConstantConstructor (constructor (fn [this, v] (assoc this :args v)) ConstantPrototype))
(defn Constant [v] (ConstantConstructor v))
(def ZERO (ConstantConstructor 0))
(def ONE (ConstantConstructor 1))
(defn Add [& args] (OperationConstructor +, (fn [_, ad] (apply Add ad)), "+", args))
(defn Subtract [& args] (OperationConstructor -, (fn [_, ad] (apply Subtract ad)), "-", args))
(defn Multiply [& args] (OperationConstructor *, (fn [a, ad]
                                                   (second (reduce (fn [[f fd] [g gd]] [(Multiply f g) (Add (Multiply f gd)
                                                                                                            (Multiply fd g))]) [ONE ZERO]
                                                                   (mapv vector a ad)))), "*", args))
(defn Negate [& args] (OperationConstructor (fn [a] (- a)), (fn [_ ad] (apply Negate ad)), "negate", args))
(defn Divide [& args] (OperationConstructor d, (fn [a, ad] (cond
                                                             (< 1 (count a))
                                                             (second (reduce (fn [[f fd] [g gd]]
                                                                               [(Divide f g) (Divide (Subtract (Multiply fd g)
                                                                                                               (Multiply f gd)) (Multiply g g))])
                                                                             (mapv vector a ad)))
                                                             (= 1 (count a)) (second (reduce (fn [[f fd] [g gd]]
                                                                                               [(Divide f g) (Divide (Subtract (Multiply fd g)
                                                                                                                               (Multiply f gd)) (Multiply g g))])
                                                                                             [ONE ZERO]
                                                                                             (mapv vector a ad)))
                                                             :else (identity ZERO))), "/", args))
(defn mean [a] (/ (reduce + a) (count a)))
(defn meanD [_ ad] (Divide (apply Add ad) (Constant (count ad))))
(defn Exp [& args] (OperationConstructor (fn [a] (Math/exp a)), (fn [a ad] (Multiply (Exp (first a)) (first ad))), "exp", args))
(defn Ln [& args] (OperationConstructor (fn [a] (Math/log (Math/abs a))), (fn [a ad] (Divide (first ad) (first a))), "ln", args))
(defn Mean [& args] (OperationConstructor (fn [& a] (mean a)), meanD, "mean", args))
(defn Varn [& args] (OperationConstructor (fn [& a] (- (/ (reduce + (mapv #(* %1 %1) a)) (count a)) (* (mean a) (mean a)))),
                                          (fn [a ad] (Multiply (Constant 2) (Subtract (apply Mean (mapv Multiply a ad)) (Multiply (apply Mean a) (apply Mean ad))))), "varn", args))

(def VariablePrototype {:evaluate       (fn [this variables] (get variables (clojure.string/lower-case (str (first (_args this))))))
                        :toString       (fn [this] (_args this))
                        :toStringSuffix (fn [this] (_args this))
                        :diff           (fn [this v] (cond (= (_args this) v) (identity ONE) :else (identity ZERO)))})
(def VariableConstructor (constructor (fn [this, v] (assoc this :args v)) VariablePrototype))
(defn Variable [v] (VariableConstructor v))
(def OBJECT_OPERATORS {'constant Constant
                       'variable Variable
                       '/        Divide,
                       'negate   Negate
                       '*        Multiply
                       '-        Subtract
                       '+        Add
                       'exp      Exp
                       'ln       Ln
                       'mean     Mean
                       'varn     Varn
                       })
(def OBJECT {"+"      Add,
             "*"      Multiply,
             "/"      Divide,
             "-"      Subtract,
             "negate" Negate})
(defn parse [token operations con variab] (
                                            cond (number? token) (con token)
                                                 (list? token) (apply (get operations (first token)) (mapv (fn [a] (parse a operations con variab)) (rest token)))
                                                 :else (variab (str token))))
(defn parseFunction [expr] (parse (read-string expr) FUNCTIONAL_OPERATORS constant variable))
(defn parseObject [expr] (parse (read-string expr) OBJECT_OPERATORS Constant Variable))
(defn -show [result]
  (if (-valid? result)
    (str "-> " (pr-str (-value result)) " | " (pr-str (apply str (-tail result))))
    "!"))
(defn tabulate [parser inputs]
  (run! (fn [input] (printf "    %-10s %s\n" (pr-str input) (-show (parser input)))) inputs))
(def *space (+char " \t\n\r"))
(def *ws (+ignore (+star *space)))
(defn sign [s tail] (if (#{\- \+} s) (cons s tail) tail))
(def *digit (+char "0123456789."))
(def *constant (+map #(Constant (read-string %)) (+str (+seqf sign (+opt (+char "-+")) (+plus *digit)))))
(def *variable (+map #(Variable %) (+str (+plus (+char "xyzXYZ")))))
(def *operator (+map #(OBJECT %) (+str (+plus (+char "+-/*negate")))))

(def *operation (+seqf #(apply (last %&) (flatten (drop-last %&)))
                       (+ignore (+char "(")) *ws
                       (+plus (+seqn 0 (+or *constant *variable (delay *operation)) *ws))
                       *ws
                       *operator
                       *ws (+ignore (+char ")"))))
;;(tabulate *variable ["x"])
(def parseObjectSuffix (+parser (+seqn 0 *ws (+or *constant *variable *operation) *ws)))
;;(print (toString (diff (Varn (Variable "x")) "x")))
(println (parseObjectSuffix "x"))
(println (evaluate (parseObjectSuffix "x") {"x" 2}))
(println (type (first (_args (parseObjectSuffix "x")))))
;;(println (evaluate(parseObjectSuffix "(    2.0 2.0     +  )"){"z" 0.0, "x" 0.0, "y" 0.0}))