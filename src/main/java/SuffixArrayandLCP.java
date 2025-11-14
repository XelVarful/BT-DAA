import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class SuffixArrayandLCP {

    // Helper class for the suffix array construction
    static class Suffix {
        int index; // Original starting index of the suffix
        int[] rank; // Rank[0] for length k, Rank[1] for length 2k

        public Suffix(int index) {
            this.index = index;
            this.rank = new int[2];
        }
    }

    /**
     * Part 1: Builds the Suffix Array (SA) in O(n log n).
     * Uses the doubling-of-comparison-length technique.
     */
    private static int[] buildSuffixArray(String text) {
        int n = text.length();
        Suffix[] suffixes = new Suffix[n];

        // 1. Initial ranking based on the first two characters (k=1)
        for (int i = 0; i < n; i++) {
            suffixes[i] = new Suffix(i);
            suffixes[i].rank[0] = text.charAt(i);
            suffixes[i].rank[1] = ((i + 1) < n) ? (text.charAt(i + 1)) : -1; // -1 for boundary
        }

        // 2. Initial Sort (k=1)
        Arrays.sort(suffixes, new Comparator<Suffix>() {
            @Override
            public int compare(Suffix a, Suffix b) {
                if (a.rank[0] != b.rank[0]) {
                    return Integer.compare(a.rank[0], b.rank[0]);
                }
                return Integer.compare(a.rank[1], b.rank[1]);
            }
        });

        // 3. Iterative Sorting and Ranking (Doubling phase)
        int[] index = new int[n]; // Maps original index to its current position in 'suffixes'

        // k will double in each iteration (4, 8, 16, ...)
        for (int k = 4; k < 2 * n; k = k * 2) {

            // Re-rank the suffixes based on the previous sort result (length k/2)
            int rank = 0;
            int prev_rank = suffixes[0].rank[0];
            suffixes[0].rank[0] = rank;
            index[suffixes[0].index] = 0; // Update the index map

            for (int i = 1; i < n; i++) {
                // If current suffix equals previous suffix (based on rank[0] and rank[1])
                if (suffixes[i].rank[0] == prev_rank && suffixes[i].rank[1] == suffixes[i-1].rank[1]) {
                    prev_rank = suffixes[i].rank[0];
                    suffixes[i].rank[0] = rank; // Assign same rank
                } else {
                    prev_rank = suffixes[i].rank[0];
                    suffixes[i].rank[0] = ++rank; // Assign new rank
                }
                index[suffixes[i].index] = i;
            }

            if (rank == n - 1) break; // All suffixes are unique, we are done

            // Update rank[1] for the new length k, then sort again
            for (int i = 0; i < n; i++) {
                int nextIndex = suffixes[i].index + k / 2;
                // Get the rank[0] of the suffix starting k/2 positions ahead
                suffixes[i].rank[1] = (nextIndex < n) ? suffixes[index[nextIndex]].rank[0] : -1;
            }

            // Final sort using the new k-length ranks
            Arrays.sort(suffixes, new Comparator<Suffix>() {
                @Override
                public int compare(Suffix a, Suffix b) {
                    if (a.rank[0] != b.rank[0]) {
                        return Integer.compare(a.rank[0], b.rank[0]);
                    }
                    return Integer.compare(a.rank[1], b.rank[1]);
                }
            });
        }

        // Extract the final Suffix Array from the sorted Suffix objects
        int[] suffixArr = new int[n];
        for (int i = 0; i < n; i++) {
            suffixArr[i] = suffixes[i].index;
        }

        return suffixArr;
    }

    /**
     * Part 2: Builds the LCP (Longest Common Prefix) array using Kasai's Algorithm in O(n).
     * LCP[i] stores LCP between SA[i-1] and SA[i].
     */
    private static int[] buildLCPArray(String text, int[] sa) {
        int n = text.length();
        int[] lcp = new int[n];
        int[] rank = new int[n]; // The inverse of SA: rank[i] is the position of suffix i in SA

        // 1. Compute the Rank Array
        for (int i = 0; i < n; i++) {
            rank[sa[i]] = i;
        }

        int k = 0; // The length of the LCP for the current pair

        // 2. Kasai's algorithm core loop
        // Iterate through suffixes in their original order (i = 0 to n-1)
        for (int i = 0; i < n; i++) {
            if (rank[i] == n - 1) {
                k = 0;
                continue; // Last suffix in SA, no comparison needed
            }

            int j = sa[rank[i] + 1]; // The suffix that comes next in the sorted order (SA)

            // The main O(n) optimization: LCP between suffix i and j is at least k-1 
            // from the previous iteration. We can skip k-1 checks.
            if (k > 0) {
                k--;
            }

            // Calculate the LCP length by comparing characters
            while (i + k < n && j + k < n && text.charAt(i + k) == text.charAt(j + k)) {
                k++;
            }

            // Store LCP length: LCP(SA[rank[i]], SA[rank[i]+1])
            lcp[rank[i]] = k;
        }

        return lcp;
    }


    /**
     * Main method to run the combined Suffix Array and LCP construction and test cases.
     */
    public static void main(String[] args) {
        System.out.println("--- Suffix Array & LCP (Kasai's Algorithm) Implementation ---");

        // --- Test Case 1: Short String (n=5) ---
        String text1 = "ababa";
        runSA_LCP(text1, "Test 1: Short String (ababa)");

        // --- Test Case 2: Medium-Length String (n=11) ---
        String text2 = "mississippi";
        runSA_LCP(text2, "Test 2: Medium String (mississippi)");

        // --- Test Case 3: Longer String (n=24) ---
        String text3 = "GCGTATGCGTGTATGCGTGCGTAT";
        runSA_LCP(text3, "Test 3: Longer String (DNA sequence example)");
    }

    // Helper method for running a test case and printing results clearly
    private static void runSA_LCP(String text, String description) {
        System.out.println("\n" + description);
        System.out.println("Text (T): " + text);

        // Build SA
        int[] sa = buildSuffixArray(text);

        // Build LCP (using Kasai's)
        int[] lcp = buildLCPArray(text, sa);

        // Prepare LCP array for standard printing (LCP[i] is LCP(SA[i-1], SA[i]))
        int[] lcp_print = new int[text.length()];
        for(int i = 0; i < text.length() - 1; i++) {
            lcp_print[i+1] = lcp[i]; // Shift by 1
        }

        System.out.println("SA (Suffix Array): " + Arrays.toString(sa));
        System.out.println("LCP (LCP Array):   " + Arrays.toString(lcp_print));
        System.out.println("Index | SA[i] | Suffix | LCP[i] (LCP(SA[i-1], SA[i]))");
        System.out.println("-----------------------------------------------------");

        for (int i = 0; i < text.length(); i++) {
            // Display '-' for LCP[0] as it's conventionally undefined
            String lcpVal = (i == 0) ? "-" : String.valueOf(lcp_print[i]);
            // Use System.out.printf() for formatted output
            System.out.printf("%5d | %5d | %-20s | %s\n",
                    i, sa[i], text.substring(sa[i]), lcpVal);
        }
    }
}