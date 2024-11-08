# Task 1.5: Extraction of Binary Regions of Interest / Connected Components
import cv2
import numpy as np

# read in noise-reduced image
image = cv2.imread("./output/kernel_size_25.jpg", cv2.IMREAD_GRAYSCALE)

# Find connected components
num_labels, labels, stats, centroids = cv2.connectedComponentsWithStats(image, connectivity=4)

# Create an output image (color) to label components
output_img = np.zeros((image.shape[0], image.shape[1], 3), dtype=np.uint8)

# Apply a single color (e.g., gray) to each component in the output image
for label in range(1, num_labels):  # Skip background (label 0)
    output_img[labels == label] = (200, 200, 200)  # Light gray color for each component

# Overlay red text labels at component centroids
for i in range(1, num_labels):  # Skip background (label 0)
    x, y = int(centroids[i][0]), int(centroids[i][1])
    cv2.putText(output_img, str(i), (x, y), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 255), 1)  # Red color (BGR: (0, 0, 255))

cv2.imwrite("./output/region_of_interest.jpg", output_img)
