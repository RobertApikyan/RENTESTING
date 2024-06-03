#!/bin/bash

xcFrameworkDirectory="${PWD}/build/XCFrameworks/release/"
remoteUrl="https://github.com/RobertApikyan/RENTESTING.git"
PUBLISH_VERSION="0.7.1"

# Initialize Git repository
echo "Initializing Git repository..."
git init "${xcFrameworkDirectory}"

# Add remote repository
echo "Adding remote repository..."
git -C "${xcFrameworkDirectory}" remote add sdk "${remoteUrl}"

# Add all files
echo "Adding all files..."
git -C "${xcFrameworkDirectory}" add .

# Commit changes
echo "Committing changes..."
git -C "${xcFrameworkDirectory}" commit -m "\"${PUBLISH_VERSION}\""

# Create tag
echo "Creating tag..."
git -C "${xcFrameworkDirectory}" tag "${PUBLISH_VERSION}"

# Push tag to remote repository
echo "Pushing tag to remote repository..."
git -C "${xcFrameworkDirectory}" push --force sdk "${PUBLISH_VERSION}"
