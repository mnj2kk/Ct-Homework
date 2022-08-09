(defn abstractVOp [f]
  (fn [vector1 vector2]
    (mapv f vector1 vector2)))
(def v+ (abstractVOp +))
(def v- (abstractVOp -))
(def v* (abstractVOp *))
(def vd (abstractVOp /))
(defn v*s [vector, s]
  (mapv (fn [x] (* x s)) vector))
(defn scalar [vector1 vector2]
  (apply + (v* vector1 vector2)))
(defn vect [x, y]
  (vector (- (* (x 1) (y 2)) (* (y 1) (x 2)))
          (- (* (x 2) (y 0)) (* (y 2) (x 0)))
          (- (* (x 0) (y 1)) (* (y 0) (x 1)))))
(defn abstractMOp [f]
  (fn [matrix1, matrix2]
    (mapv (abstractVOp f) matrix1 matrix2)
    ))
(def m+ (abstractMOp +))
(def m- (abstractMOp -))
(def m* (abstractMOp *))
(def md (abstractMOp /))
(defn transpose [matrix]
  (apply mapv vector matrix))
(defn m*v [matrix, v]
  (mapv (partial scalar v) matrix))
(defn m*s [matrix, s]
  (mapv (fn [x] (v*s x s)) matrix))
(defn m*m [matrix1, matrix2]
  (mapv (fn [x] (m*v (transpose matrix2) x)) matrix1))
(defn abstractCuboidOp [f]
  (fn [cuboid1, cuboid2] (mapv (abstractMOp f) cuboid1 cuboid2)
    ))
(def c+ (abstractCuboidOp +))
(def c- (abstractCuboidOp -))
(def c* (abstractCuboidOp *))
(def cd (abstractCuboidOp /))