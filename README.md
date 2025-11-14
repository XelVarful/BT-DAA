# String Algorithms: Suffix Array and LCP Array Construction

## 1. Project Overview

This project implements two fundamental algorithms in string processing: the Suffix Array (SA) and the Longest Common Prefix (LCP) Array. The goal was to achieve optimal time complexity for both components: $O(n \log n)$ for the SA and $O(n)$ for the LCP array.

The final implementation is contained within the SuffixArrayandLCP.java class.

## 2. Implementation Details

### 2.1 Suffix Array Construction (SA)

The Suffix Array construction uses the doubling-of-comparison-length method, often referenced as a variation of the D-S algorithm.

Approach: The suffixes are iteratively sorted based on $k$-length prefixes. In each step, the comparison length $k$ is doubled (from 1 to 2, 4, 8, etc.).

Time Complexity: The overall time complexity is $O(n \log n)$, as there are $\log n$ sorting iterations, and each sort takes linear time $O(n)$ if implemented efficiently (using counting sort), or $O(n \log n)$ if using standard comparison sorting (which is sufficient here for demonstration).

### 2.2 LCP Array Construction (Kasai's Algorithm)

The LCP Array, where $LCP[i]$ is the length of the longest common prefix between $SA[i-1]$ and $SA[i]$, is built using Kasai's linear-time algorithm.

Optimization: This algorithm relies on the fact that if a suffix $S[i]$ has an LCP of length $k$ with the preceding suffix in the array, then the suffix $S[i+1]$ will have an LCP of at least $k-1$ with its predecessor. This property allows for character comparisons to be amortized, resulting in a highly efficient, single-pass $O(n)$ calculation.

Required Data Structure: The algorithm first computes the Rank Array (the inverse of the SA) to quickly find the predecessor of any given suffix.

## 3. Complexity Analysis

The combined algorithm complexity is dominated by the Suffix Array construction.

Component

Algorithm Used

Time Complexity

Space Complexity

Suffix Array (SA)

Doubling/Inductive Sorting

$O(n \log n)$

$O(n)$

LCP Array (LCP)

Kasai's Algorithm

$O(n)$

$O(n)$

Overall

SA + LCP

$O(n \log n)$

$O(n)$

## 4. Testing and Results

The implemented code was tested using the main method within the class, which executes three distinct test cases to verify correctness across different string characteristics.

Test Case

String

Length (n)

Characteristics

Test 1

ababa

5

Simple, repetitive pattern.

Test 2

mississippi

11

Classic test case with repeated characters.

Test 3

GCGTATGCGTGTATGCGTGCGTAT

24

Longer sequence, simulating biological data (e.g., DNA).

The output for each test case displays the string, the computed Suffix Array (SA), the LCP Array (LCP), and a clean table showing the sorted suffixes along with their LCP values.

## 5. Execution

The project is structured as a single Java file (SuffixArrayandLCP.java).

Compile: Compile the file using a standard Java compiler (e.g., javac SuffixArrayandLCP.java).

Run: Execute the compiled class (e.g., java SuffixArrayandLCP).

The test results will be printed directly to the console.
