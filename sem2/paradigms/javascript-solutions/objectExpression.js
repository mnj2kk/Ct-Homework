"use strict";


const OPERATORS = new Map([
    ["+", [Add, 2, (a, b) => a + b, (a, b, v) => new Add(a.diff(v), b.diff(v))]],
    ["-", [Subtract, 2, (a, b) => a - b, (a, b, v) => new Subtract(a.diff(v), b.diff(v))]],
    ["/", [Divide, 2, (a, b) => a / b, (a, b, v) =>
        new Divide(new Subtract(
                new Multiply(a.diff(v), b),
                new Multiply(b.diff(v), a)),
            new Multiply(b, b))]],
    ["*", [Multiply, 2, (a, b) => a * b, (a, b, v) =>
        new Add(
            new Multiply(a.diff(v), b),
            new Multiply(b.diff(v), a))]],
    ["min3", [Min3, 3, (a, b, c) => Math.min(a, b, c), function () {
        let arr = [].slice.apply(arguments);
        return new Min3(arr.forEach(elem => {
            elem.diff()
        }))
    }]],
    ["max5", [Max5, 5, (a, b, c, d, e) => Math.max(a, b, c, d, e), function () {
        let arr = [].slice.apply(arguments);
        return new Max5(arr.forEach(elem => {
            elem.diff()
        }))
    }
    ]],
    ["negate",
        [Negate, 1, (a) => -a, (a, v) => new Negate(a.diff(v))]],
    ["sinh",
        [Sinh, 1,
            (a) => (Math.sinh(a)),
            (a, v) => new Multiply(new Cosh(a), a.diff(v))]],
    ["cosh",
        [Cosh, 1,
            (a) => (Math.cosh(a)),
            (a, v) => new Multiply(new Sinh(a), a.diff(v))]]]);
const VARIABLES = new Map([["x", 0], ["y", 1], ["z", 2]]);
const BRACKETS = new Set([")", "("]);

function Const(value) {
    this.toString = () => (String)(value);
    this.evaluate = function () {
        return value;
    }
    this.diff = function () {
        return new Const(0);
    }
    this.prefix = () => (String)(value);
}

function Operation(type, ...args) {
    this.type = type
    this.args = new Array(...args)
}

Operation.prototype.evaluate = function (...vars) {
    return OPERATORS.get(this.type)[2](...this.args.map(elem => elem.evaluate(...vars)));

}
Operation.prototype.toString = function () {
    return this.args.map(elem => elem.toString()).join(" ") + " " + this.type;
};
Operation.prototype.diff = function (v) {
    return OPERATORS.get(this.type)[3](...this.args, v);
}
Operation.prototype.prefix = function () {
    return "(" + this.type + " " + this.args.map(elem => elem.prefix()).join(" ") + ")";
}
Sinh.prototype = Object.create(Operation.prototype)

function Sinh() {
    Operation.call(this, "sinh", ...arguments)
}

Cosh.prototype = Object.create(Operation.prototype)

function Cosh() {
    Operation.call(this, "cosh", ...arguments)
}

Min3.prototype = Object.create(Operation.prototype)

function Min3() {
    Operation.call(this, "min3", ...arguments)
}

Max5.prototype = Object.create(Operation.prototype)

function Max5() {
    Operation.call(this, "max5", ...arguments)
}

Negate.prototype = Object.create(Operation.prototype)

function Negate() {
    Operation.call(this, "negate", ...arguments);
}

Add.prototype = Object.create(Operation.prototype)

function Add() {
    Operation.call(this, "+", ...arguments);

}

Subtract.prototype = Object.create(Operation.prototype)

function Subtract() {
    Operation.call(this, "-", ...arguments);

}

Divide.prototype = Object.create(Operation.prototype)

function Divide() {
    Operation.call(this, "/", ...arguments);
}

Multiply.prototype = Object.create(Operation.prototype)

function Multiply() {
    Operation.call(this, "*", ...arguments);
}

function Variable(op) {
    this.evaluate = function () {
        return (arguments[VARIABLES.get(op)]);
    };
    this.toString = function () {
        return op;
    };
    this.diff = function (v) {
        return (v === op) ? new Const(1) : new Const(0);
    }
    this.prefix = function () {
        return op;
    }
}

const parse = input => {
    input = input.split(' ').filter(t => t.length > 0);
    let stack = [];
    input.map(s => {
            if (OPERATORS.has(s)) {
                let op = OPERATORS.get(s);
                let elems = stack.slice(-op[1]);
                stack = stack.slice(0, -op[1]);
                stack.push(new op[0](...elems));
            } else if (VARIABLES.has(s)) {
                stack.push(new Variable((s)));

            } else {
                stack.push(new Const(Number.parseInt(s)));
            }
        }
    )
    return stack.pop()
};

const parsePrefix = input => {
    input = input.replace(/\(/g, ' ( ').replace(/\)/g, ' ) ').split(" ").filter(t => t.length > 0);

    let balance = 0;
    let mustBeAOperation = true;
    if (input.length === 0) {
        throw  new WrongInputError();
    }
    let stack = input.reduceRight(function (stack, token, index) {
            if (BRACKETS.has(token)) {
                if (token === ")") {
                    balance++;
                    stack.push(token);
                    mustBeAOperation = true;
                } else {
                    balance--;
                    if (mustBeAOperation) {
                        throw  new ParseError(token, index, `because ${stack[stack.length - 1]} not a operation`);
                    }
                }
                if (balance < 0) {
                    throw  new ParseError(token, index);
                }
            } else if (OPERATORS.has(token)) {
                let op = OPERATORS.get(token);
                let elems = stack.slice(stack.lastIndexOf(")", -op[1]) + 1, stack.length).reverse();
                if (elems.length !== op[1]) {
                    throw new ParseError(token, index, "because not enough elements");
                }
                stack = stack.slice(0, -op[1] - 1);
                stack.push(new op[0](...elems))
                mustBeAOperation = false
            } else if (VARIABLES.has(token)) {
                stack.push(new Variable((token), index));
            } else {
                if (!isNaN(token)) {
                    stack.push(new Const(Number.parseInt(token)));
                } else {
                    throw new ParseError(token, index)
                }
            }

            return stack;
        }, []
    )
    stack = stack.filter(t => !BRACKETS.has(t));
    if (balance !== 0 || stack.length !== 1) {
        throw  new ParseError(stack[0], 0);
    }
    return stack[0];
};

class ParseError extends Error {
    constructor(token, index, cause = "") {
        super(`ParseError :unexpected token  ${token} at ${index} ${cause}`);
    }
}

class WrongInputError extends Error {
    constructor() {
        super(`No input`);
    }
}