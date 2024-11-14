import cv2
import numpy as np
import matplotlib.pyplot as plt

# Task 2.1: Spatial Domain
image = cv2.imread("jennifer.jpg")

kernel_size = (15, 15)
variance = 20

smoothed_image = cv2.GaussianBlur(image, kernel_size, variance)

cv2.imwrite("./output/jennifer_spatial.jpg", smoothed_image)

# Task 2.3: Frequency Domain Filtering for
channels = cv2.split(image)
filtered_channels = []

for channel in channels:
    fft_channel = np.fft.fft2(channel)

    # shift the zero frequency component to the center
    fft_channel_shifted = np.fft.fftshift(fft_channel)

    # create a Gaussian filter the same size as the channel
    gaussian_kernel = cv2.getGaussianKernel(kernel_size[0], variance)
    gaussian_kernel_2d = gaussian_kernel @ gaussian_kernel.T

    # pad the Gaussian filter to match the size of the image channel
    gaussian_kernel_padded = np.pad(gaussian_kernel_2d, 
                                    ((0, fft_channel_shifted.shape[0] - gaussian_kernel_2d.shape[0]), 
                                     (0, fft_channel_shifted.shape[1] - gaussian_kernel_2d.shape[1])), 
                                    mode='constant', constant_values=0)

    # shift the padded filter in the frequency domain
    fft_gaussian_padded_shifted = np.fft.fftshift(np.fft.fft2(gaussian_kernel_padded))

    # apply the low-pass filter to the channel
    low_pass_filtered = fft_channel_shifted * fft_gaussian_padded_shifted

    # perform the inverse FFT to get the filtered channel in the spatial domain
    ifft_filtered = np.fft.ifft2(np.fft.ifftshift(low_pass_filtered))

    # take the real part and normalize it
    filtered_channel = np.real(ifft_filtered)
    filtered_channel = np.clip(filtered_channel, 0, 255).astype(np.uint8)

    # append the filtered channel to the list
    filtered_channels.append(filtered_channel)

filtered_image_color = cv2.merge(filtered_channels)

cv2.imwrite("./output/jennifer_freq.jpg", filtered_image_color)
