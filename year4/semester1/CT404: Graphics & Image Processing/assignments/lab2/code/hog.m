close all;
clear all;
clc;

% Load necessary package
pkg load image;

% Load and preprocess the image
img = imread('truck.jpg'); % Replace with the path to your image
if size(img, 3) == 3
    img = rgb2gray(img); % Convert to grayscale if the image is in color
end

% Resize the image to 128x64 for standardization
img = imresize(img, [128, 64]);

% Gamma normalization
gamma = 0.9; % Example gamma value
img = im2double(img) .^ gamma; % Convert to double and apply gamma normalization

figure;
imshow(img);
title('Gamma-normalized Image');

% Parameters for HOG
cell_size = 4;         % Size of each cell (4x4 pixels)
block_size = 2;        % Number of cells per block (2x2 cells)
num_bins = 9;          % Number of orientation bins (0° to 180°)
bin_size = 180 / num_bins;

% Compute gradients using Sobel operator on the entire image
Gx = imfilter(double(img), [-1 0 1; -2 0 2; -1 0 1], 'conv'); % Horizontal gradient
Gy = imfilter(double(img), [-1 -2 -1; 0 0 0; 1 2 1], 'conv'); % Vertical gradient

% Compute gradient magnitude and orientation
magnitude = sqrt(Gx.^2 + Gy.^2);
orientation = atan2d(Gy, Gx);
orientation(orientation < 0) += 180; % Convert orientation to [0, 180] range

% Display gradient magnitude and orientation
figure;
imshow(magnitude, []);
title('Gradient Magnitude of the Image');

% Display gradient orientation using quiver
figure;
imshow(img); % Show original image as background
hold on;

% Downsample the quiver plot for better visualization, e.g., every 4th pixel
step = 4; % Adjust the step to make the plot less dense if needed

% Plot gradient orientations using Gx and Gy as the vector components
[x, y] = meshgrid(1:step:size(Gx, 2), 1:step:size(Gy, 1));
quiver(x, y, Gx(1:step:end, 1:step:end), Gy(1:step:end, 1:step:end), 'color', 'r', 'LineWidth', 0.5);

title('Gradient Orientation Visualization Using Quiver');
hold off;

% Calculate number of cells in each direction
num_cells_row = floor(size(img, 1) / cell_size);
num_cells_col = floor(size(img, 2) / cell_size);

% Preallocate the HOG feature array
hog_features = zeros(1, num_cells_row * num_cells_col * num_bins);

% Calculate HOG features for each cell
counter = 1;
for row = 1:cell_size:(size(img, 1) - cell_size + 1)
    for col = 1:cell_size:(size(img, 2) - cell_size + 1)
        % Extract cell gradient magnitudes and orientations
        cell_magnitude = magnitude(row:row+cell_size-1, col:col+cell_size-1);
        cell_orientation = orientation(row:row+cell_size-1, col:col+cell_size-1);

        % Calculate histogram for the cell manually
        cell_histogram = zeros(1, num_bins);
        for bin = 1:num_bins
            % Define the orientation range for the bin
            angle_min = (bin - 1) * bin_size;
            angle_max = bin * bin_size;

            % Find pixels within the bin range
            mask = (cell_orientation >= angle_min) & (cell_orientation < angle_max);

            % Sum the magnitudes of pixels within the current orientation bin
            cell_histogram(bin) = sum(cell_magnitude(mask));
        end

        % Store cell histogram in hog_features
        hog_features(counter:counter+num_bins-1) = cell_histogram;
        counter = counter + num_bins;
    end
end

% Block normalization: Divide the HOG features into blocks of 2x2 cells and normalize
block_histograms = [];
for row = 1:num_cells_row - block_size + 1
    for col = 1:num_cells_col - block_size + 1
        % Compute the index in hog_features for the current block
        start_index = ((row - 1) * num_cells_col + col - 1) * num_bins + 1;
        end_index = start_index + block_size * block_size * num_bins - 1;

        % Extract the block of HOG features
        block = hog_features(start_index:end_index);

        % Normalize the block histogram
        norm_block = block / sqrt(sum(block .^ 2) + 1e-6);

        % Append the normalized block to block_histograms
        block_histograms = [block_histograms, norm_block];
    end
end

% Final HOG descriptor for the entire image
hog_descriptor = block_histograms;

% Display results
disp("HOG Descriptor for the entire image:");
disp(size(hog_descriptor));
disp("Length of HOG descriptor vector:");
disp(length(hog_descriptor));

% Visualization of HOG Features on a Blank Canvas
figure;
imshow(ones(size(img)), []); % Blank figure with same size as image
hold on;

% Visualize dominant gradient direction per cell
for row = 1:cell_size:(size(img, 1) - cell_size + 1)
    for col = 1:cell_size:(size(img, 2) - cell_size + 1)
        % Get cell gradient data
        cell_magnitude = magnitude(row:row+cell_size-1, col:col+cell_size-1);
        cell_orientation = orientation(row:row+cell_size-1, col:col+cell_size-1);

        % Calculate histogram for the cell manually
        cell_histogram = zeros(1, num_bins);
        for bin = 1:num_bins
            % Define the orientation range for the bin
            angle_min = (bin - 1) * bin_size;
            angle_max = bin * bin_size;

            % Find pixels within the bin range
            mask = (cell_orientation >= angle_min) & (cell_orientation < angle_max);

            % Sum the magnitudes of pixels within the current orientation bin
            cell_histogram(bin) = sum(cell_magnitude(mask));
        end

        % Find the dominant orientation
        [max_value, dominant_bin] = max(cell_histogram);
        dominant_orientation = (dominant_bin - 1) * bin_size + bin_size / 2; % Center of the bin

        % Convert the orientation to radians for plotting
        angle_rad = deg2rad(dominant_orientation);

        % Plot the dominant gradient direction using quiver
        scale_factor = 0.3;
        quiver(col + cell_size / 2, row + cell_size / 2, ...
               scale_factor * max_value * cos(angle_rad), ...
               scale_factor * max_value * sin(angle_rad), ...
               'color', 'w', 'LineWidth', 1);
    end
end

title('HOG Features Visualization for Dominant Orientation per Cell');
hold off;

