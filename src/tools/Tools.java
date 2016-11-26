package tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Tools {	
	public static void showErrorDialog(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public static Alert showWarningDialog(String message) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText(null);
		alert.setContentText(message);
		return alert;
	}

	public static Alert showConfirmationDialog(String message) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText(null);
		alert.setContentText(message);
		return alert;
	}

	public static void showInformationDialog(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.show();
		// return alert;
	}

	public static int parseInt(String value, int defaultValue) {
		int result;
		try {
			result = Integer.parseInt(value);
		} catch (NumberFormatException nfe) {
			result = defaultValue;
		}
		return result;
	}

	public static void executeCmd(String cmd, File output) throws Exception {
		BufferedWriter writer = null;
		Process p = null;
		BufferedReader reader = null;
		try {
			writer = new BufferedWriter(new FileWriter(output));
			p = Runtime.getRuntime().exec(cmd);

			// Flush error output
			try {
				p.getErrorStream().close();
			} catch (Exception e) {
			}

			// Write output
			String line = null;
			reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = reader.readLine()) != null) {
				if (writer != null) {
					writer.write(line);
					writer.newLine();
				} else
					System.out.println(line);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (Exception e) {
			}
			try {
				if (p != null)
					p.getErrorStream().close();
			} catch (Exception e) {
			}
			try {
				if (p != null)
					p.getInputStream().close();
			} catch (Exception e) {
			}
			try {
				if (p != null)
					p.getOutputStream().close();
			} catch (Exception e) {
			}
			try {
				if (p != null)
					p.waitFor();
			} catch (Exception e) {
			}
		}
	}

	public static ArrayList<String> executeCmd(String cmd, boolean displayOutput) {
		ArrayList<String> output = new ArrayList<String>();

		if (cmd == null || cmd.length() <= 0) {
			if (displayOutput)
				System.out.println("Command is empty!");
			else
				output.add("Command is empty!");
			return output;
		}

		Process p = null;
		BufferedReader reader = null;
		try {
			p = Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			String line;
			if (p != null) {
				// Flush error output
				try {
					p.getErrorStream().close();
				} catch (Exception e) {
				}

				// Flush output
				reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
				line = "";
				try {
					while ((line = reader.readLine()) != null) {
						if (displayOutput)
							System.out.println(line);
						else
							output.add(line);
					}
				} catch (Exception e) {
				}
				try {
					p.getInputStream().close();
				} catch (Exception e) {
				}

				// Flush input
				try {
					p.getOutputStream().close();
				} catch (Exception e) {
				}

				// Wait end of treatment
				try {
					p.waitFor();
				} catch (Exception e) {
				}
			}
		}
		return output;
	}

	public synchronized static ArrayList<String> executeCmd(String[] cmd, File workingDirectory) {
		if (cmd == null || cmd.length <= 0)
			return new ArrayList<String>();
		final ArrayList<String> result = new ArrayList<String>();

		// Create process
		ProcessBuilder pb = new ProcessBuilder(cmd);
		if (workingDirectory != null && workingDirectory.exists())
			pb.directory(workingDirectory);

		// Execute process
		Process p = null;
		try {
			p = pb.start();

			final BufferedReader output = new BufferedReader(new InputStreamReader(p.getInputStream()));
			final BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));

			// Empty output
			Thread outputThread = new Thread() {
				public void run() {
					String line = "";
					try {
						while ((line = output.readLine()) != null) {
							result.add(line);
							System.out.println(line);
						}
					} catch (Exception e) {
					}
				}
			};
			outputThread.start();

			// Empty error
			Thread errorThread = new Thread() {
				public void run() {
					String line = "";
					try {
						while ((line = error.readLine()) != null)
							System.err.println(line);
					} catch (Exception e) {
					}
				}
			};
			errorThread.start();

			try {
				p.waitFor();
				outputThread.join();
				errorThread.join();
			} catch (Exception e) {
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return result;
		} finally {
			if (p != null) {
				try {
					p.getErrorStream().close();
				} catch (Exception e) {
				}
				try {
					p.getInputStream().close();
				} catch (Exception e) {
				}
				try {
					p.getOutputStream().close();
				} catch (Exception e) {
				}
			}
		}
	}

	public static Date parseDate(String d, String format) {
		try {
			if (d == null || d.isEmpty())
				return null;
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			return formatter.parse(d);
		} catch (Exception e) {
			return null;
		}
	}

	public static File parseDirectory(String str) {
		if (str == null || str.length() <= 0)
			return null;
		File f = new File(str);
		if (!f.exists())
			return null;
		if (!f.isDirectory())
			return null;
		return f;
	}

	public static long parseLong(String str) {
		if (str == null || str.length() <= 0)
			return -1;
		try {
			return Long.parseLong(str);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	public static int parseSafeInt(String value, int defaultValue) {
		int result;
		try {
			result = Integer.parseInt(value);
		} catch (NumberFormatException nfe) {
			result = defaultValue;
		}
		return result;
	}

	public static boolean isNumeric(String str) {
		if (str == null || str =="") {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isDigit(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean containsNumbers(String s){
		Pattern p = Pattern.compile("([0-9])");
		Matcher m = p.matcher(s);
		return m.find();
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
}
