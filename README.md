# Expression Evaluator

<ins>This repo is a re-upload</ins>

In this project, we had to code a fully functional calculator that could follow the rules of PEMDAS, 
without explicitly adding brackets.

In Expression Evaluator, I had to pass the basic functionality of a calculator using integers and doubles. 
Furthermore, this was paired with static error checking, in which the error would be detected before running:

**1)** "Op_Error": incomplete operations

**2)** "Paren_Error": imbalanced parenthesis

**3)** "Data_Error": invalid number format

While detecting these errors was difficult, learning regex and the calculation algorithm using both stacks and queues 
was a difficult mountain to climb. The calculator needed to support implicit multiplication, 
detect negation against subtraction, and apply everything to the data.

### Reflection
ExpressionEvaluator to date is one of the most challenging computer science projects I have done. The whole idea behind how a 
calculator works 'behind the scenes' is extremely interesting, and a lot more complex than I thought. 
I still wonder how to solve the last impMultNeg1Test, because that was the last test I failed. I tried to figure out the 
solution for a long time, however, changing the ReGex to solve impMultNeg1 would cause other tests to fail.
