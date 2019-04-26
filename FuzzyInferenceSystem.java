/*
 * A program that uses FAHP to diagnose diseases
 */
package fyp_test;

import java.util.*;
import java.util.Arrays;

/**
 *
 * @author Olubunmi
 */
public class FuzzyInferenceSystem {

    private int[] weight;
    private final int MALARIA = 0;
    private final int PNEUMONIA = 1;
    private final int TUBERCULOSIS = 2;
    private final int TYPHOID = 3;
    // a mxn array that represent the fuzzy rules the system depends on
    private final String rulesDB[][] = {
        {"-", "-", "-", "VL", "-", "-", "-", "-", "VL", "VL", "VL", "-", "VL", "-", "-", "-", "VL", "-", "malaria", "VL"},
        {"-", "-", "-", "L", "-", "-", "-", "-", "L", "L", "L", "-", "L", "-", "-", "-", "L", "-", "malaria", "L"},
        {"-", "-", "-", "M", "-", "-", "-", "-", "M", "M", "M", "-", "M", "-", "-", "-", "M", "-", "malaria", "M"},
        {"-", "-", "-", "H", "-", "-", "-", "-", "H", "H", "H", "-", "H", "-", "-", "-", "H", "-", "malaria", "H"},
        {"-", "-", "-", "VH", "-", "-", "-", "-", "VH", "VH", "VH", "-", "VH", "-", "-", "-", "VH", "-", "malaria", "VH"},
        //
        {"VL", "-", "VL", "VL", "-", "VL", "VL", "VL", "VL", "VL", "VL", "VL", "VL", "-", "-", "-", "VL", "-", "malaria", "L"},
        {"L", "-", "L", "L", "-", "L", "L", "L", "L", "L", "L", "L", "L", "-", "-", "-", "L", "-", "malaria", "M"},
        {"M", "-", "M", "M", "-", "M", "M", "M", "M", "M", "M", "M", "M", "-", "-", "-", "M", "-", "malaria", "H"},
        {"H", "-", "H", "H", "-", "H", "H", "H", "H", "H", "H", "H", "H", "-", "-", "-", "H", "-", "malaria", "VH"},
        {"VH", "-", "VH", "VH", "-", "VH", "VH", "VH", "VH", "VH", "VH", "VH", "VH", "-", "-", "-", "VH", "-", "malaria", "M"},
        //
        {"-", "-", "VL", "VL", "-", "VL", "-", "-", "VL", "VL", "VL", "VL", "-", "-", "VL", "VL", "-", "-", "pneumonia", "VL"},
        {"-", "-", "L", "L", "-", "L", "-", "-", "L", "L", "L", "L", "-", "-", "L", "L", "-", "-", "pneumonia", "L"},
        {"-", "-", "M", "M", "-", "M", "-", "-", "M", "M", "M", "M", "-", "-", "M", "M", "-", "-", "pneumonia", "M"},
        {"-", "-", "H", "H", "-", "H", "-", "-", "H", "H", "H", "H", "-", "-", "H", "H", "-", "-", "pneumonia", "H"},
        {"-", "-", "VH", "VH", "-", "VH", "-", "-", "VH", "VH", "VH", "VH", "-", "-", "VH", "VH", "-", "-", "pneumonia", "VH"},
        //
        {"-", "-", "VL", "VL", "-", "VL", "VL", "VL", "VL", "VL", "VL", "VL", "VL", "-", "VL", "VL", "VL", "-", "pneumonia", "L"},
        {"-", "-", "L", "L", "-", "L", "L", "L", "L", "L", "L", "L", "L", "-", "L", "L", "L", "-", "pneumonia", "M"},
        {"-", "-", "M", "M", "-", "M", "M", "M", "M", "M", "M", "M", "M", "-", "M", "M", "M", "-", "pneumonia", "H"},
        {"-", "-", "H", "H", "-", "H", "H", "H", "H", "H", "H", "H", "H", "-", "H", "H", "H", "-", "pneumonia", "VH"},
        {"-", "-", "VH", "VH", "-", "VH", "VH", "VH", "VH", "VH", "VH", "VH", "VH", "-", "VH", "VH", "VH", "-", "pneumonia", "VH"},
        //
        {"-", "-", "VL", "VL", "-", "VL", "-", "-", "VL", "VL", "-", "VL", "-", "-", "VL", "VL", "-", "VL", "tuberculosis", "VL"},
        {"-", "-", "L", "L", "-", "L", "-", "-", "L", "L", "-", "L", "-", "-", "L", "L", "-", "L", "tuberculosis", "L"},
        {"-", "-", "M", "M", "-", "M", "-", "-", "M", "M", "-", "M", "-", "-", "M", "M", "-", "M", "tuberculosis", "M"},
        {"-", "-", "H", "H", "-", "H", "-", "-", "H", "H", "-", "H", "-", "-", "H", "H", "-", "H", "tuberculosis", "H"},
        {"-", "-", "VH", "VH", "-", "VH", "-", "-", "VH", "VH", "-", "VH", "-", "-", "VH", "VH", "-", "VH", "tuberculosis", "VH"},
        //
        {"VL", "VL", "VL", "VL", "-", "VL", "-", "-", "VL", "VL", "-", "VL", "-", "VL", "VL", "VL", "-", "-", "tuberculosis", "L"},
        {"L", "L", "L", "L", "-", "L", "-", "-", "L", "L", "-", "L", "-", "L", "L", "L", "-", "-", "tuberculosis", "M"},
        {"M", "M", "M", "M", "-", "M", "-", "-", "M", "M", "-", "M", "-", "M", "M", "M", "-", "-", "tuberculosis", "H"},
        {"H", "H", "H", "H", "-", "H", "-", "-", "H", "H", "-", "H", "-", "H", "H", "H", "-", "-", "tuberculosis", "VH"},
        {"VH", "VH", "VH", "VH", "-", "VH", "-", "-", "VH", "VH", "-", "VH", "-", "VH", "VH", "VH", "-", "-", "tuberculosis", "VH"},
        //
        {"VL", "-", "-", "-", "VL", "-", "-", "VL", "VL", "VL", "VL", "VL", "-", "-", "-", "-", "-", "-", "typhoid", "VL"},
        {"L", "-", "-", "-", "L", "-", "-", "L", "L", "L", "L", "L", "-", "-", "-", "-", "-", "-", "typhoid", "L"},
        {"M", "-", "-", "-", "M", "-", "-", "M", "M", "M", "M", "M", "-", "-", "-", "-", "-", "-", "typhoid", "M"},
        {"H", "-", "-", "-", "H", "-", "-", "H", "H", "H", "H", "H", "-", "-", "-", "-", "-", "-", "typhoid", "H"},
        {"VH", "-", "-", "-", "VH", "-", "-", "VH", "VH", "VH", "VH", "VH", "-", "-", "-", "-", "-", "-", "typhoid", "VH"},
        //
        {"VL", "-", "-", "VL", "VL", "VL", "VL", "VL", "VL", "VL", "VL", "VL", "VL", "VL", "-", "VL", "VL", "VL", "typhoid", "L"},
        {"L", "-", "-", "L", "L", "L", "L", "L", "L", "L", "L", "L", "L", "L", "-", "L", "L", "L", "typhoid", "M"},
        {"M", "-", "-", "M", "M", "M", "M", "M", "M", "M", "M", "M", "M", "M", "-", "M", "M", "M", "typhoid", "H"},
        {"H", "-", "-", "H", "H", "H", "H", "H", "H", "H", "H", "H", "H", "H", "-", "H", "H", "H", "typhoid", "H"},
        {"VH", "-", "-", "VH", "VH", "VH", "VH", "VH", "VH", "VH", "VH", "VH", "VH", "VH", "-", "VH", "VH", "VH", "typhoid", "VH"},
        //rules from questionnaire
        {"M", "-", "-", "L", "-", "-", "L", "M", "-", "L", "L", "-", "M", "M", "-", "-", "L", "-", "typhoid", "H"},//1
        {"-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "VL", "VL", "VL", "-", "-", "-", "L", "-", "malaria", "M"},
        {"-", "-", "-", "-", "-", "M", "-", "-", "-", "-", "H", "M", "-", "-", "-", "-", "-", "-", "malaria", "H"},
        {"-", "-", "-", "-", "-", "M", "-", "-", "-", "-", "H", "M", "-", "-", "-", "-", "-", "-", "typhoid", "M"},
        {"-", "-", "M", "-", "-", "H", "-", "-", "-", "-", "H", "-", "-", "-", "-", "-", "-", "-", "malaria", "H"},
        {"-", "-", "M", "-", "-", "H", "-", "-", "-", "-", "H", "-", "-", "-", "-", "-", "-", "-", "typhoid", "VL"},
        {"-", "-", "-", "-", "-", "-", "-", "-", "M", "H", "M", "VH", "-", "-", "-", "-", "-", "-", "malaria", "H"},
        {"-", "-", "-", "-", "-", "-", "-", "-", "M", "H", "M", "VH", "-", "-", "-", "-", "-", "-", "typhoid", "M"},
        {"-", "-", "-", "-", "-", "-", "-", "-", "-", "VH", "H", "-", "-", "-", "-", "-", "-", "-", "malaria", "H"},//6
        {"-", "-", "-", "L", "-", "M", "-", "-", "-", "L", "-", "-", "-", "-", "-", "-", "-", "-", "tuberculosis", "VL"},
        {"-", "-", "-", "L", "-", "M", "-", "-", "-", "L", "-", "-", "-", "-", "-", "-", "-", "-", "pneumonia", "M"},
        {"-", "-", "-", "-", "-", "-", "VL", "-", "-", "-", "M", "M", "L", "-", "-", "-", "L", "-", "typhoid", "L"},
        {"-", "-", "-", "-", "-", "-", "VL", "-", "-", "-", "M", "M", "L", "-", "-", "-", "L", "-", "malaria", "H"},
        {"-", "-", "-", "-", "-", "VL", "L", "-", "L", "M", "H", "L", "L", "-", "-", "-", "-", "-", "malaria", "H"},//9
        {"-", "-", "-", "-", "-", "VL", "L", "-", "L", "M", "H", "L", "L", "-", "-", "-", "-", "-", "typhoid", "L"},//9
        {"-", "-", "-", "-", "-", "-", "-", "-", "M", "VH", "H", "-", "-", "-", "-", "-", "-", "-", "malaria", "VH"},
        {"-", "-", "-", "-", "-", "-", "-", "-", "M", "VH", "H", "-", "-", "-", "-", "-", "-", "-", "typhoid", "M"},
        {"-", "-", "M", "-", "-", "H", "-", "-", "-", "-", "M", "-", "-", "-", "-", "-", "-", "-", "tuberculosis", "M"},
        {"-", "-", "-", "-", "-", "-", "H", "VH", "-", "M", "-", "-", "-", "-", "-", "-", "-", "-", "malaria", "M"},
        {"-", "-", "-", "-", "-", "-", "-", "-", "-", "VH", "-", "-", "-", "-", "-", "-", "VH", "-", "malaria", "H"},
        {"-", "-", "-", "-", "-", "-", "-", "-", "-", "M", "H", "H", "-", "-", "-", "-", "-", "-", "malaria", "VH"},
        {"VH", "-", "-", "-", "-", "-", "-", "-", "M", "-", "-", "H", "-", "-", "-", "-", "H", "-", "malaria", "VH"},//15
        {"VH", "-", "-", "-", "-", "-", "-", "-", "M", "-", "-", "H", "-", "-", "-", "-", "H", "-", "typhoid", "VH"},
        {"-", "-", "VH", "-", "-", "H", "-", "-", "-", "-", "-", "-", "-", "-", "H", "-", "-", "-", "tuberculosis", "H"},//16
        {"-", "-", "VH", "-", "-", "H", "-", "-", "-", "-", "-", "-", "-", "-", "H", "-", "-", "-", "pneumonia", "H"},//16
        {"L", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "M", "-", "-", "-", "H", "-", "malaria", "M"},
        {"-", "-", "L", "-", "-", "H", "-", "-", "-", "-", "-", "-", "-", "-", "VL", "-", "-", "-", "malaria", "M"},
        {"-", "-", "L", "-", "-", "H", "-", "-", "-", "-", "-", "-", "-", "-", "VL", "-", "-", "-", "tuberculosis", "VL"},
        {"L", "-", "M", "-", "-", "-", "-", "-", "H", "H", "M", "M", "M", "-", "-", "-", "L", "-", "malaria", "H"}, //{"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
    };
    //this array holds the membership function of the input symptoms
    double mf[][] = {
        {-42.5, -5, 0, 37.5}, //VL
        {-16, 21.5, 26.5, 64}, //L
        {10.5, 48, 53, 90.5}, //M
        {36.75, 74.25, 79.25, 116.75}, //H
        {63, 100.5, 105.5, 143} //VH
    };

    double eigenValues[][] = {
        {0.0336, 0.0000, 0.0456, 0.1219, 0.0000, 0.0260, 0.0215, 0.0223, 0.0697, 0.2692, 0.1266, 0.1176, 0.0696, 0.0000, 0.0228, 0.0000, 0.0536, 0.0000}, //malaria
        {0.0172, 0.0000, 0.1797, 0.1613, 0.0000, 0.1406, 0.0252, 0.0263, 0.0384, 0.0473, 0.0830, 0.0304, 0.0285, 0.0000, 0.1694, 0.0341, 0.0184, 0.0000}, //pneumonia
        {0.0114, 0.1920, 0.0908, 0.0900, 0.0000, 0.0816, 0.0160, 0.0169, 0.0240, 0.0307, 0.0535, 0.0210, 0.0179, 0.0000, 0.0995, 0.0223, 0.0126, 0.2197}, //tuberculosis
        {0.0406, 0.0000, 0.0493, 0.1153, 0.0277, 0.0244, 0.0242, 0.0303, 0.0795, 0.2287, 0.1085, 0.0920, 0.0572, 0.0093, 0.0000, 0.0131, 0.0488, 0.0512} //typhoid
    };

    public FuzzyInferenceSystem(int[] weight) {
        this.weight = weight;
    }

    private double Fuzzifier(String ling_var, int symptom_weight) {
        //setting up the membership function matrix reference
        int X = 0;
        switch (ling_var) {
            case "VL":
                X = 0;
                break;
            case "L":
                X = 1;
                break;
            case "M":
                X = 2;
                break;
            case "H":
                X = 3;
                break;
            case "VH":
                X = 4;
                break;
        }
        int a = 0;
        int b = 1;
        int c = 2;
        int d = 3;
        int w = symptom_weight;
        double i = 0;
        double j = 0;
        i = ((w - mf[X][a]) / (mf[X][b] - mf[X][a]));
        j = ((mf[X][d] - w) / (mf[X][d] - mf[X][c]));
        return Math.max(Math.min(Math.min(i, 1), j), 0);
    }

    private double Defuzzifier(double[] aggregate) {
        double numerator = 0;
        double denominator = 0;
        double[] centre = {-2.5, 24, 50.5, 76.75, 103};
        for (int i = 0; i < 5; i++) {
            numerator = numerator + (integral(aggregate[i]) * centre[i]);
            denominator = denominator + integral(aggregate[i]);
        }
        return Math.min(100, (numerator / denominator));
    }

    private double Compositor(List<Double> md) { //average
        Iterator iterator = md.iterator();
        double sum = 0;
        while (iterator.hasNext()) {
            sum = sum + (double) iterator.next();
        }
        double ave = sum / md.size();
        return ave;
    }

    private double Normalizer(double degree, int symptom, String disease) {
        int X = -1;
        switch (disease) {
            case "malaria":
                X = 0;
                break;
            case "pneumonia":
                X = 1;
                break;
            case "tuberculosis":
                X = 2;
                break;
            case "typhoid":
                X = 3;
                break;
        }

        return degree * eigenValues[X][symptom];
    }

    private double[] Aggregator(List<Object[]> md) {
        Iterator iterator = md.iterator();
        double sum[] = {0, 0, 0, 0, 0};
        double counter[] = {0, 0, 0, 0, 0};
        while (iterator.hasNext()) {
            Object[] set = (Object[]) iterator.next();
            switch ((String) set[0]) {
                case "VL":
                    sum[0] = sum[0] + (double) set[1];
                    counter[0]++;
                    break;
                case "L":
                    sum[1] = sum[1] + (double) set[1];
                    counter[1]++;
                    break;
                case "M":
                    sum[2] = sum[2] + (double) set[1];
                    counter[2]++;
                    break;
                case "H":
                    sum[3] = sum[3] + (double) set[1];
                    counter[3]++;
                    break;
                case "VH":
                    sum[4] = sum[4] + (double) set[1];
                    counter[4]++;
                    break;

            }
        }
        double[] average = new double[5];
        for (int i = 0; i < 5; i++) {
            average[i] = sum[i] / counter[i];
        }
        return average;
    }

    private double[] rssAggregator(List<Object[]> md) {
        Iterator iterator = md.iterator();
        double sum[] = {0, 0, 0, 0, 0};
        while (iterator.hasNext()) {
            Object[] set = (Object[]) iterator.next();
            switch ((String) set[0]) {
                case "VL":
                    sum[0] = sum[0] + Math.pow((double) set[1], 2);
                    break;
                case "L":
                    sum[1] = sum[1] + Math.pow((double) set[1], 2);
                    break;
                case "M":
                    sum[2] = sum[2] + Math.pow((double) set[1], 2);
                    break;
                case "H":
                    sum[3] = sum[3] + Math.pow((double) set[1], 2);
                    break;
                case "VH":
                    sum[4] = sum[4] + Math.pow((double) set[1], 2);
                    break;

            }
        }
        double[] rss = new double[5];
        for (int i = 0; i < 5; i++) {
            rss[i] = Math.sqrt(sum[i]);
        }
        return rss;
    }

    private double[] maxAggregator(List<Object[]> md) {
        Iterator iterator = md.iterator();
        double max[] = {0, 0, 0, 0, 0};
        while (iterator.hasNext()) {
            Object[] set = (Object[]) iterator.next();
            switch ((String) set[0]) {
                case "VL":
                    max[0] = Math.max(max[0], (double) set[1]);
                    break;
                case "L":
                    max[1] = Math.max(max[1], (double) set[1]);
                    break;
                case "M":
                    max[2] = Math.max(max[2], (double) set[1]);
                    break;
                case "H":
                    max[3] = Math.max(max[3], (double) set[1]);
                    break;
                case "VH":
                    max[4] = Math.max(max[4], (double) set[1]);
                    break;

            }
        }
        return max;
    }

    private List InferenceEngine() {
        List<ArrayList<Object[]>> inference = new ArrayList<>();
        ArrayList<Object[]> malaria = new ArrayList<>();
        ArrayList<Object[]> pneumonia = new ArrayList<>();
        ArrayList<Object[]> tuberculosis = new ArrayList<>();
        ArrayList<Object[]> typhoid = new ArrayList<>();
        for (int i = 0; i < rulesDB.length; i++) { //loops through each rule
            List<Double> md = new ArrayList<Double>(); //temporary holding for each rule
            for (int j = 0; j < rulesDB[i].length - 2; j++) { //loops through each statement of each rule
                String ling_var = rulesDB[i][j];
                String disease = rulesDB[i][18];
                if (!ling_var.equals("-")) {
                    if (weight[j] >= 0) {
                        md.add(Normalizer(Fuzzifier(ling_var, weight[j]), j, disease));
                    } else {
                        md.add(0.0);
                    }
                }
            }
            if (Compositor(md) > 0) {
                Object[] fuzzySet = new Object[2];
                fuzzySet[0] = rulesDB[i][19];  //severity
                fuzzySet[1] = Compositor(md);
                switch (rulesDB[i][18]) {
                    case "malaria":
                        malaria.add(fuzzySet);
                        break;
                    case "pneumonia":
                        pneumonia.add(fuzzySet);
                        break;
                    case "tuberculosis":
                        tuberculosis.add(fuzzySet);
                        break;
                    case "typhoid":
                        typhoid.add(fuzzySet);
                        break;
                }
            }
        }
        inference.add(malaria);
        inference.add(pneumonia);
        inference.add(tuberculosis);
        inference.add(typhoid);
        return inference;
    }

    public double integral(double h) {
        return (80 * (h - ((15 * h * h) / 32)));
    }

    public int[] order(double[] temp) {
        int[] order = new int[4];
        for (int i = 0; i <= 3; i++) {
            double current = temp[i];
            int position = 1;
            for (int j = 0; j <= 3; j++) {
                if (current < temp[j]) {
                    position++;
                }
            }
            order[i] = position;
        }
        return order;
    }

    public double[] DiagnosisManager() {
        List<ArrayList<Object[]>> inference = InferenceEngine();
        //
        double[] diagnosis = new double[4];
        diagnosis[MALARIA] = Defuzzifier(Aggregator(inference.get(MALARIA)));
        diagnosis[PNEUMONIA] = Defuzzifier(Aggregator(inference.get(PNEUMONIA)));
        diagnosis[TUBERCULOSIS] = Defuzzifier(Aggregator(inference.get(TUBERCULOSIS)));
        diagnosis[TYPHOID] = Defuzzifier(Aggregator(inference.get(TYPHOID)));
        return diagnosis;
    }
}
