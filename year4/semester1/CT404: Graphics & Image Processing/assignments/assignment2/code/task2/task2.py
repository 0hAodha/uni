import cv2
import numpy as np
import matplotlib.pyplot as plt

# Task 2.1: Spatial Domain
image = cv2.imread("../../Task2.jpg")

kernel_size = (15, 15)
variance = 2

smoothed_image = cv2.GaussianBlur(image, kernel_size, variance)

cv2.imwrite("./output/1_spatial_domain.jpg", smoothed_image)

# Task 2.2: Frequency Domain Low-Pass Filter
gaussian_kernel = cv2.getGaussianKernel(kernel_size[0], variance)
gaussian_kernel_2d = gaussian_kernel @ gaussian_kernel.T
fft_gaussian = np.fft.fft2(gaussian_kernel_2d)

# shift zero frequency component to center
fft_gaussian_shifted = np.fft.fftshift(fft_gaussian)

# calculate the magnitude spectrum for visualization
magnitude_spectrum = np.log(np.abs(fft_gaussian_shifted) + 1)

# Plot the magnitude spectrum (Frequency Domain Representation)
plt.imshow(magnitude_spectrum, cmap='gray')
plt.axis('off')
plt.savefig("./output/2_frequency_domain_low-pass_filter.jpg", bbox_inches='tight', pad_inches=0)

# Task 2.3: Frequency Domain Filtering
