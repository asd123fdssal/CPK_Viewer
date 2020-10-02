package utils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class DataIO {

	private String filePath;
	private String[][] arrData;
	private String[] arrColumn;

	private static class LazyHolder {
		public static final DataIO INSTANCE = new DataIO();
	}

	public static DataIO getInstance() {
		return LazyHolder.INSTANCE;
	}

	public DataIO() {
		JFileChooser chooser = new JFileChooser();
		int res = chooser.showOpenDialog(null);
		if (res != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, "File has not selected! \r\nProgram is will be exit.", "ERROR", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} else {
			filePath = chooser.getSelectedFile().getPath();
		}
	}

	public void getDatas() {
		if (filePath == "")
			return;

		Path path = Paths.get(filePath);

		if (Files.exists(path)) {
			try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ)) {
				ByteBuffer bb = ByteBuffer.allocate((int) Files.size(path));
				channel.read(bb);
				bb.flip();
				strToList(Charset.defaultCharset().decode(bb).toString());
			} catch (IOException e) {
				e.getStackTrace();
			}
		}
	}

	private void strToList(String datas) {
		if (datas == "")
			return;

		String[] rows;
		String[][] seperatedData;

		rows = datas.split("\n");
		seperatedData = new String[rows.length - 1][];
		for (int i = 1; i < rows.length; i++) {
			seperatedData[i - 1] = rows[i].split(",");
		}
		arrData = seperatedData;
		arrColumn = rows[0].split(",");
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String[][] getArrData() {
		return arrData;
	}

	public String[] getArrColumn() {
		return arrColumn;
	}

}
