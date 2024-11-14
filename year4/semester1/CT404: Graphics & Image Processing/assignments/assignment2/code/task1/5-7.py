import cv2
import numpy as np

# Load and pre-process binary image
binary_image = cv2.imread("./output/kernel_size_17.jpg", cv2.IMREAD_GRAYSCALE)
binary_image = cv2.medianBlur(binary_image, 3)

# Step 1.5: Extraction of Binary Regions of Interest / connected components
num_labels, labels, stats, centroids = cv2.connectedComponentsWithStats(binary_image, connectivity=8)

# Initialize an empty mask for filtered regions
filtered_mask = np.zeros(binary_image.shape, dtype=np.uint8)

total_globules = 0

# Task 1.6: Filtering of Fat Globules
for i in range(1, num_labels):
    area = stats[i, cv2.CC_STAT_AREA]

    # Calculate compactness
    perimeter = cv2.arcLength(cv2.findContours((labels == i).astype(np.uint8), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)[0][0], True)
    compactness = (perimeter ** 2) / area if area > 0 else 0

    if (300 < area) and (compactness < 27):
        total_globules += 1
        filtered_mask[labels == i] = 255

cv2.imwrite("./output/filtered_fat_globules.jpg", filtered_mask)
print("Total globules: " + str(total_globules))

# Task 1.7: Calculation of the Fat Area
# Total area of the image in pixels (excluding the background)
total_image_area = binary_image.shape[0] * binary_image.shape[1]

# Total fat area (in pixels)
fat_area = np.sum(filtered_mask == 255)

# Calculate fat percentage
fat_percentage = (fat_area / total_image_area) * 100
print(f"Fat Area Percentage: {fat_percentage:.2f}%")
