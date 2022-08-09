"use strict";
const VARIABLES = {"x": 0, "y": 1, "z": 2}
const cnst = value => () => value;
const variable = op => (...args) => args[VARIABLES[op]];
const e = () => cnst(Math.E)();
const pi = () => cnst(Math.PI)();
const operation = f => (...args) => (...vars) => f(...args.map(elem => elem(...vars)));
const subtract = operation((a, b) => a - b);
const add = operation((a, b) => a + b);
const divide = operation((a, b) => a / b);
const multiply = operation((a, b) => a * b);
const negate = operation((a) => -a);
const OPERATORS = {"+": [add, 2], "-": [subtract, 2], "/": [divide, 2], "*": [multiply, 2], "negate": [negate, 1]}
const CONSTS = {"pi": pi, "e": e}
const parse = input => {
    input = input.split(' ').filter(t => t.length > 0);
    let stack = [];
    input.map(s => {
            if (s in OPERATORS) {
                let elems = stack.slice(-OPERATORS[s][1]);
                stack = stack.slice(0, -OPERATORS[s][1]);
                OPERATORS[s][1].stack.push(OPERATORS[s][0](...elems));

            } else if (s in VARIABLES) {
                stack.push(variable((s)));

            } else if (s in CONSTS) {
                stack.push(CONSTS[s])
            } else {
                stack.push(cnst(Number.parseInt(s)));
            }
        }
    )
    return stack.pop()
};
