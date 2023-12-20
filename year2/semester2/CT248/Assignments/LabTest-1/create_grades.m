function [grades] = create_grades(rows, columns, min, max, seed) 
    rng(seed);
    grades = randi([min max], rows, columns);
end