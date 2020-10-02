package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataCalculator {

	private List<Double> arrValue;
	private List<Integer> arrCntValue;

	private static class LazyHolder {
		public static final DataCalculator INSTANCE = new DataCalculator();
	}

	public static DataCalculator getInstance() {
		return LazyHolder.INSTANCE;
	}

	public DataCalculator() {
	}

	public double getMin(List<Double> list) {
		return Collections.min(list);
	}

	public double getMax(List<Double> list) {
		return Collections.max(list);
	}

	public double getAve(List<Double> list) {
		Double result = 0.0;
		for (int i = 0; i < list.size(); i++) {
			result += list.get(i);
		}
		return result / list.size();
	}

	public double getStdev(List<Double> list, int option) {

		if (list.size() < 2)
			return Double.NaN;

		double sum = 0.0;
		double diff;
		double meanValue = getAve(list);

		for (int i = 0; i < list.size(); i++) {
			diff = list.get(i) - meanValue;
			sum += diff * diff;
		}
		return Math.sqrt(sum / (list.size() - option));
	}

	public double getVar(List<Double> list) {

		double avg = getAve(list);
		double total = 0;

		for (int i = 0; i < list.size(); i++) {
			total += (list.get(i) - avg) * (list.get(i) - avg);
		}

		return total / list.size();
	}

	public double getCPK(double lsl, double usl, double ave, double stdev) {
		double temp = (usl - lsl) / (6 * stdev);

		return temp - Math.abs(((lsl + usl) / 2 - ave) / (3 * stdev));
	}

	public void sortData(List<Double> list) {
		arrValue = new ArrayList<Double>();
		arrCntValue = new ArrayList<Integer>();

		for (int i = 0; i < list.size(); i++) {
			int idx = arrValue.indexOf(list.get(i));
			if (idx > -1) {
				arrCntValue.set(idx, arrCntValue.get(idx) + 1);
				continue;
			}

			arrValue.add(list.get(i));
			arrCntValue.add(1);
		}
	}

	public List<Double> getArrValue() {
		return arrValue;
	}

	public List<Integer> getArrCntValue() {
		return arrCntValue;
	}

}
