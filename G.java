import java.util.*;


class FlowerSurvey {
    // Class สำหรับจัดการ Union-Find หรือ Disjoint Set Union (DSU)
    static class UnionFind {
        private int[] parent, rank;


        // Initialize the Union-Find structure
        public UnionFind(int size) {
            parent = new int[size];
            rank = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
                rank[i] = 1;
            }
        }


        // Find with path compression
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }


        // Union by rank
        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX != rootY) {
                if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                } else if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
            }
        }


        // Check if two elements are in the same set
        public boolean connected(int x, int y) {
            return find(x) == find(y);
        }
    }


    // ตรวจสอบความสอดคล้อง
    public static boolean isConsistent(int n, int[][] labeledPairs, int[][] surveyAnswers, int[] labels) {
        UnionFind uf = new UnionFind(n);


        // สร้าง union จากคำตอบของผู้ตอบแบบสอบถาม
        for (int[] pair : surveyAnswers) {
            uf.union(pair[0], pair[1]);
        }


        // ตรวจสอบความสอดคล้องจากรูปภาพที่ติดป้ายกำกับ
        for (int[] pair : labeledPairs) {
            if (labels[pair[0]] != labels[pair[1]]) {
                if (uf.connected(pair[0], pair[1])) {
                    return false; // ขัดแย้งกับป้ายกำกับ
                }
            }
        }


        return true;
    }


    public static void main(String[] args) {
        int n = 7; // จำนวนภาพทั้งหมด A, B, C, D, E, F, G
        int[] labels = {-1, -1, -1, -1, -1, -1, -1}; // -1 หมายถึงไม่มีป้ายกำกับ


        // ระบุป้ายกำกับ A: ดอกกุหลาบ, B: ดอกลิลลี่, C: กล้วยไม้
        labels[0] = 1; // A
        labels[1] = 2; // B
        labels[2] = 3; // C


        // ตัวอย่างข้อมูลผู้ตอบแบบสอบถาม 1
        int[][] labeledPairs1 = {
            {0, 1}, // A และ B มีป้ายกำกับ
        };


        int[][] surveyAnswers1 = {
            {0, 3}, // A และ D ถูกระบุว่าเป็นชนิดเดียวกัน
            {2, 5}, // C และ F ถูกระบุว่าเป็นชนิดเดียวกัน
            {4, 6}, // E และ G ถูกระบุว่าเป็นชนิดเดียวกัน
            {3, 5}  // D และ F ถูกระบุว่าเป็นชนิดเดียวกัน
        };


        // ตรวจสอบความสอดคล้องสำหรับผู้ตอบแบบสอบถาม 1
        boolean consistent1 = isConsistent(n, labeledPairs1, surveyAnswers1, labels);
        System.out.println("Survey 1 Consistent: " + consistent1); // Expected: false


        // ตัวอย่างข้อมูลผู้ตอบแบบสอบถาม 2
        int[][] labeledPairs2 = {
            {0, 1}, // A และ B มีป้ายกำกับ (ทั้งคู่คือดอกกุหลาบ)
        };


        int[][] surveyAnswers2 = {
            {0, 1}, // A และ B ถูกระบุว่าเป็นชนิดเดียวกัน
            {0, 4}, // A และ E ถูกระบุว่าเป็นชนิดเดียวกัน
            {5, 6}, // F และ G ถูกระบุว่าเป็นชนิดเดียวกัน
            {3, 5}  // D และ F ถูกระบุว่าเป็นชนิดเดียวกัน
        };


        // ตรวจสอบความสอดคล้องสำหรับผู้ตอบแบบสอบถาม 2
        boolean consistent2 = isConsistent(n, labeledPairs2, surveyAnswers2, labels);
        System.out.println("Survey 2 Consistent: " + consistent2); // Expected: true
    }
}
// แสดงผล
//Survey 1 Consistent: false
//Survey 2 Consistent: true
