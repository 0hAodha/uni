close all;
clear all;
clc;

pkg load image; % Load the image package for handling PGM and JPEG files

% Description:
% This script implements face recognition using the Eigenfaces approach
% for the ORL face database and adds visualization of the mean face, eigenfaces,
% and face reconstruction using different numbers of eigenfaces.
% Additionally, it includes testing on an unseen face image that is not part of the training set.

% Parameters
data_dir = '../data/';  % Directory containing face data
num_subjects = 1;        % Number of subjects in the dataset
train_images_per_subject = 9;  % Number of images per subject for training
test_images_per_subject = 1;   % Number of images per subject for testing
image_size = [112, 92];   % Size of each image (height, width)
num_train_images = num_subjects * train_images_per_subject;  % Total number of training images
K = 9;                   % Number of eigenvectors (Eigenfaces) to use
given_index = 3;          % Example index for reconstruction

% Ask user if they want to use saved data or upload new data
use_saved_data = input('Would you like to use saved data (1) or upload new data (0)? ');

if use_saved_data == 1
    % Load saved data from MAT file
    load('face_data.mat');
else
    % Step 1: Load training images and construct matrix A
    A = zeros(prod(image_size), num_train_images);  % Initialize matrix A for training images
    train_index = 1;

    % Read each training image and convert it to a column vector
    for subject = 1:num_subjects
        subject_dir = fullfile(data_dir, sprintf('s%d', subject));
        for img_num = 1:train_images_per_subject
            % Load the training image
            img_path = fullfile(subject_dir, sprintf('%d.pgm', img_num));
            img = imread(img_path);
            % Flatten the image into a column vector and add to matrix A
            A(:, train_index) = reshape(img, [], 1);
            train_index += 1;
        end
    end

    % Step 2: Mean-centering the training data
    mean_image = double(mean(A, 2));  % Compute mean of each pixel across all training images
    A = double(A) - mean_image;  % Subtract mean face from all training images to mean-center

    % Save the face data to a MAT file
    save('face_data.mat', 'A', 'mean_image');
end

% Step 3: Compute the large covariance matrix L and the reduced covariance matrix C
L = A * A';  % Compute the large covariance matrix (for explanation purposes)
fprintf('Size of L: %dx%d\n', size(L, 1), size(L, 2));

C = A' * A;  % Compute the reduced covariance matrix C

% Step 4: Compute eigenvectors of C
[vectorC, valueC] = eig(C);
d = diag(valueC);
[d, index] = sort(-d);  % Sort eigenvalues in descending order
vectorC = vectorC(:, index);  % Reorder eigenvectors accordingly

% Step 5: Compute Eigenfaces (eigenvectors of L) from eigenvectors of C
vectorL = A * vectorC(:, 1:K);  % Compute the eigenvectors of L from C
vectorL_all = A * vectorC(:, 1:end);  % Compute all eigenvectors of L from C

% Normalize each column of vectorL and vectorL_all to unit length
for i = 1:size(vectorL_all, 2)
    if i <= size(vectorL, 2)
        vectorL(:, i) = vectorL(:, i) / norm(vectorL(:, i));
    end
    vectorL_all(:, i) = vectorL_all(:, i) / norm(vectorL_all(:, i));
end

% Step 6: Finding Coefficients: Project training images into the face space (compute coefficients)
coeff_all = A' * vectorL_all;  % Coefficients using all eigenfaces
coeff = coeff_all(:, 1:K);  % Coefficients using the selected number of eigenfaces

% Step 7: Create a model for each subject by averaging the coefficients of their training images
model = zeros(num_subjects, K);  % Initialize model to store mean coefficients
for i = 1:num_subjects
    model(i, :) = mean(coeff((i - 1) * train_images_per_subject + 1 : i * train_images_per_subject, :));
end

% Step 8: Test accuracy on all test images
correct_matches = 0;
num_test_images = num_subjects * test_images_per_subject;

for subject = 1:num_subjects
    % Load the test image for the current subject
    subject_dir = fullfile(data_dir, sprintf('s%d', subject));
    test_img_path = fullfile(subject_dir, sprintf('%d.pgm', train_images_per_subject + 1));
    test_img = imread(test_img_path);
    test_img_vector = double(reshape(test_img, [], 1));
    test_img_vector = test_img_vector - mean_image;

    % Project the test image into the face space
    test_coeff = test_img_vector' * vectorL;

    % Find the closest subject in the model using Euclidean distance
    min_distance = inf;
    predicted_subject = -1;
    for i = 1:num_subjects
        distance = norm(test_coeff - model(i, :));
        if distance < min_distance
            min_distance = distance;
            predicted_subject = i;
        end
    end

    % Check if the prediction is correct
    if predicted_subject == subject
        correct_matches += 1;
    end
end

% Calculate and display accuracy
accuracy = (correct_matches / num_test_images) * 100;
fprintf('Test Accuracy: %.2f%%\n', accuracy);

% Display Step: Visualization

% Display the entire matrix A as an image for visualization
figure;
imshow(reshape(A, [image_size(1) * num_subjects, image_size(2) * train_images_per_subject]), []);
title('Full Matrix A as an Image');

% Display the mean face image
figure;
imshow(reshape(mean_image, image_size), []);
title('Mean Face Image');

% Step 9: Reconstruction of an example face using all eigenfaces and only top K
original_face = A(:, given_index) + mean_image;  % Original face image (un-normalized)

% Reconstruct using all eigenfaces
coeff_all_example = coeff_all(given_index, :);  % Coefficients using all eigenfaces
reconstructed_face_all = mean_image + vectorL_all * coeff_all_example';

% Reconstruct using top K eigenfaces
coeff_K_example = coeff(given_index, :);  % Coefficients using top K eigenfaces
reconstructed_face_K_example = mean_image + vectorL * coeff_K_example';

% Display original and reconstructed faces
figure;
subplot(1, 3, 1);
imshow(reshape(original_face, image_size), []);
title(sprintf('%d Given Test Image', given_index));

subplot(1, 3, 2);
imshow(reshape(reconstructed_face_all, image_size), []);
title('Reconstructed with All Eigenfaces');

subplot(1, 3, 3);
imshow(reshape(reconstructed_face_K_example, image_size), []);
title(sprintf('Reconstructed with %d Eigenfaces', K));


% Step 10: Test image not part of training set

% Path to a new test image that is not part of the training dataset
new_test_img_path = '../data/unseen.jpg';
new_test_img = imread(new_test_img_path);

% Resize the new test image if necessary to match training image size
if size(new_test_img, 1) ~= image_size(1) || size(new_test_img, 2) ~= image_size(2)
    new_test_img = imresize(new_test_img, image_size);
end

% Convert to grayscale if needed
if size(new_test_img, 3) == 3
    new_test_img = rgb2gray(new_test_img);
end

new_test_img_vector = double(reshape(new_test_img, [], 1));  % Flatten the test image
new_test_img_vector = new_test_img_vector - mean_image;  % Mean-center the test image

% Project the test image into the face space
new_test_coeff = new_test_img_vector' * vectorL;  % Coefficients of the test image in face space

% Find the closest subject in the model using Euclidean distance
min_distance = inf;
predicted_subject = -1;
for i = 1:num_subjects
    distance = norm(new_test_coeff - model(i, :));
    if distance < min_distance
        min_distance = distance;
        predicted_subject = i;
    end
end

% Load the closest match image
closest_match_img_path = fullfile(data_dir, sprintf('s%d', predicted_subject), '1.pgm');
closest_match_img = imread(closest_match_img_path);

% Display the original new test image, its reconstruction, and the closest match
figure;
subplot(1, 2, 1)
imshow(new_test_img)
title('Original Image');
subplot(1, 2, 2)
imshow(closest_match_img)
title('Best Match');

% Credit for Dataset
fprintf('\nWhen using these images, please give credit to Olivetti Research Laboratory.\n');
fprintf('Reference: F. Samaria and A. Harter, "Parameterisation of a stochastic model for human face identification"\n');
fprintf('2nd IEEE Workshop on Applications of Computer Vision, Dec 1994, Sarasota (Florida).\n');

