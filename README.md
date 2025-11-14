# String Algorithms: Suffix Array and LCP Array Construction

## 1. Project Overview

This project implements two fundamental algorithms in string processing: the Suffix Array (SA) and the Longest Common Prefix (LCP) Array. The primary objective was to achieve the optimal time complexity for these structures: $O(n \log n)$ for the SA construction and $O(n)$ for the LCP array construction.

The entire implementation is housed within the single Java class: SuffixArrayandLCP.java.

## 2. Implementation Details

### 2.1 Suffix Array Construction (buildSuffixArray)

The SA construction utilizes an $O(n \log n)$ approach based on the doubling-of-comparison-length technique.

Approach: Suffixes are iteratively sorted by considering prefixes of length $k$, where $k$ is doubled in each step ($k = 1, 2, 4, 8, \dots$). The Suffix helper class manages the two ranking components: rank[0] (rank of the current half) and rank[1] (rank of the subsequent half).

Time Complexity: The $O(n \log n)$ bound is achieved due to $\log n$ passes of sorting.

### 2.2 LCP Array Construction (buildLCPArray)

The LCP Array, which stores the length of the longest common prefix between $SA[i-1]$ and $SA[i]$, is built using Kasai's linear-time algorithm.

Data Structures: The buildLCPArray method first computes the Rank Array (rank), which serves as the inverse of the SA. This allows for $O(1)$ lookups to find the predecessor of any suffix $S[i]$ in the sorted array.

Optimization (Key Property): The algorithm exploits the property that the LCP of suffix $S[i+1]$ with its predecessor is at least $\text{LCP}(S[i], \text{pred}) - 1$. This crucial optimization ensures that the total number of character comparisons is limited to $2n$, yielding an overall $O(n)$ time complexity.

## 3. Complexity Analysis

The complexity of the solution is dominated by the initial Suffix Array construction, yielding an overall complexity of $O(n \log n)$.

| Component | Algorithm Used | Time Complexity | Space Complexity | 
| :--- | :--- | :--- | :--- | 
| **Suffix Array (SA)** | Doubling/Inductive Sorting | $O(n \log n)$ | $O(n)$ | 
| **LCP Array (LCP)** | Kasai's Algorithm | $O(n)$ | $O(n)$ | 
| **Overall** | SA + LCP | $O(n \log n)$ | $O(n)$ |

## 4. Testing and Results

The solution's correctness was verified by running the main method with three diverse test cases. The output format includes the final SA and LCP arrays, along with a detailed table showing the sorted suffixes and their corresponding LCP lengths.

| Test Case | String | Length (`n`) | Characteristics | 
| :--- | :--- | :--- | :--- | 
| **Test 1** | `"ababa"` | 5 | Simple, repetitive pattern. | 
| **Test 2** | `"mississippi"` | 11 | Classic test case with repeated characters. | 
| **Test 3** | `"GCGTATGCGTGTATGCGTGCGTAT"` | 24 | Longer sequence, simulating biological data. |
## 5. Execution

The project is structured as a single, self-contained Java file.

Compile: Use the Java compiler: ``javac SuffixArrayandLCP.java``

Run: Execute the compiled class: ``java SuffixArrayandLCP``

All test results are printed directly to the standard output ``System.out``
