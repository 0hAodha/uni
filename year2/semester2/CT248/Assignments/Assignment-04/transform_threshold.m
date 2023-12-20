function [img] = transform_threshold(img, threshold) 
% function which converts the picture to binary format where any value 
% above the threshold is white (1), and all values equal to or below are
% black (0). 

    % looping through each element in the matrix, and setting it to 1 if
    % above the threshold, 0 otherwise
    for i = 1:numel(img) 
        if img(i) > threshold 
            img(i) = 1; 
        else 
            img(i) = 0; 
        end
    end
    
    % casting the matrix to type logical once each element is either 1 or 0
    img = logical(img);
end