import pandas as pd
from sklearn.model_selection import KFold, cross_validate
from sklearn.ensemble import RandomForestRegressor
from sklearn.metrics import make_scorer, mean_squared_error
import pandas as pd
import numpy as np
import seaborn as sns
import matplotlib.pyplot as plt
from sklearn.model_selection import GridSearchCV, KFold
from sklearn.ensemble import RandomForestRegressor
from sklearn.metrics import make_scorer, mean_squared_error

# Set random seed for reproducibility
random_seed = 42

"""Load data"""

data = pd.read_csv('/content/drive/MyDrive/steel.csv')
X = data.drop(columns=['tensile_strength'])
y = data['tensile_strength']

"""Random Forest Regressor with default hyperparameters"""

model = RandomForestRegressor(random_state=random_seed)
kf = KFold(n_splits=10, shuffle=True, random_state=42)


# Perform 10-fold cross-validation with MSE
mse_results = cross_validate(
    model, X, y, cv=kf, scoring='neg_mean_squared_error', return_train_score=True
)
average_train_mse = mse_results["train_score"].mean()
average_test_mse = mse_results["test_score"].mean()

# Perform 10-fold cross-validation with R²
r2_results = cross_validate(
    model, X, y, cv=kf, scoring='r2', return_train_score=True
)
average_train_r2 = r2_results["train_score"].mean()
average_test_r2 = r2_results["test_score"].mean()

print(f"Average Training MSE: {average_train_mse:.4f}\nAverage Testing  MSE: {average_test_mse:.4f}\n")
print(f"Average Training  R²: {average_train_r2:.4f}\nAverage Testing   R²: {average_test_r2:.4f}")

"""Hyperparameter tuning"""

# Initialize the Random Forest Regressor with the random seed
model = RandomForestRegressor(random_state=random_seed)

# Define the parameter grid for n_estimators and max_depth
param_grid = {
    'n_estimators': [1, 3, 5, 10, 50, 100, 250, 500, 1000],  # New values for n_estimators
    'max_depth': [1, 5, 10, 20, 30, None]  # New values for max_depth
}

# Initialize GridSearchCV with 10-fold cross-validation and scoring by R2
grid_search_r2 = GridSearchCV(
    model, param_grid, cv=10, scoring='r2', return_train_score=True
)

# Fit GridSearchCV to the data for R2
grid_search_r2.fit(X, y)

# Get the results for R2
results_r2 = grid_search_r2.cv_results_

# Initialize GridSearchCV with MSE scoring
grid_search_mse = GridSearchCV(
    model, param_grid, cv=10, scoring='neg_mean_squared_error', return_train_score=True
)

# Fit GridSearchCV to the data for MSE
grid_search_mse.fit(X, y)

# Get the results for MSE
results_mse = grid_search_mse.cv_results_

# Extract average R2 and MSE scores for each combination of hyperparameters
mean_train_r2 = results_r2['mean_train_score'].reshape(len(param_grid['n_estimators']), len(param_grid['max_depth']))
mean_test_r2 = results_r2['mean_test_score'].reshape(len(param_grid['n_estimators']), len(param_grid['max_depth']))

mean_train_mse = results_mse['mean_train_score'].reshape(len(param_grid['n_estimators']), len(param_grid['max_depth']))
mean_test_mse = results_mse['mean_test_score'].reshape(len(param_grid['n_estimators']), len(param_grid['max_depth']))

# Plot R² heatmap
plt.figure(figsize=(10, 6))
sns.heatmap(mean_test_r2, annot=True, cmap="coolwarm", xticklabels=param_grid['max_depth'], yticklabels=param_grid['n_estimators'], cbar_kws={'label': 'R² Score'})
plt.title("R² Score for Each Hyperparameter Combination")
plt.xlabel("Max Depth")
plt.ylabel("Number of Estimators")
plt.show()

# Plot MSE heatmap with decimal formatting
plt.figure(figsize=(10, 6))
sns.heatmap(mean_test_mse, annot=True, fmt='.4f', cmap="coolwarm", xticklabels=param_grid['max_depth'], yticklabels=param_grid['n_estimators'], cbar_kws={'label': 'Mean Squared Error'})
plt.title("MSE for Each Hyperparameter Combination")
plt.xlabel("Max Depth")
plt.ylabel("Number of Estimators")
plt.show()

# Identify the optimal hyperparameters for R² and MSE
best_r2_params = grid_search_r2.best_params_
best_mse_params = grid_search_mse.best_params_

print(f"Optimal Hyperparameters for R²: {best_r2_params}")
print(f"Optimal Hyperparameters for MSE: {best_mse_params}")
