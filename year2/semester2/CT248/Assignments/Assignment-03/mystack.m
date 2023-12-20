function[push, pop, peek] = mystack()
% function to return handles to the subfunction push, pop, & peek
    push = @push;
    pop = @pop;
    peek = @peek;
end

function [stack] = push(stack, value)
% function to push a value onto the stack at location 1 and return the
% stack
    stack = [value; stack];
end

function [stack] = pop(stack)
% function to pop the value at location 1 off the stack and return the
% stack
    stack(1) = [];
end

function [value] = peek(stack)
% function to return the top value from the stack (arrau location 1)
% returns NaN if there is no value at location 1
    if isempty(stack)
        value = NaN;
    else
        value = stack(1);
    end
end