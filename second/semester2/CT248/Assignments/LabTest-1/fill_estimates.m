function [grades] = fill_estimates(grades)
    for i = 1:size(grades, 2) 
        % making copy
        copy = grades(:, i);
        nonzero = 0; % count of nonzero elements to calculate avg
        sum = 0;    % sum of nonzero elements

        % looping through copy and counting number of nonzero elements
        for j = 1:size(copy,1) 
            if copy(j) ~= 0
                nonzero = nonzero + 1;
                sum = sum + copy(j);
            end 
        end
        
        avg = round(sum / nonzero);

        % looping through original and replacing any nonzero with average 
        for j = 1:size(copy,1)
            if grades(j,i) == 0
                grades(j,i) = avg; 
            end
        end
    end
end