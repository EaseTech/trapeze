/**
 * 
 */
package org.easetech.trapeze;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;

/**
 * @author anuj
 * READ : http://confluent.io/docs/current/kafka/deployment.html
 * 
 * http://confluent.io/docs/current/kafka/deployment.html
 *
 */
public class Kafka {
	
	private static ArrayList<String> commands = new ArrayList<String>();

	public static void main(String[] arg) {

		commands.add("add-apt-repository ppa:saiarcot895/myppa");
		commands.add("sudo apt-get update");
		//commands.add("apt-get -y install apt-fast");
		commands.add("sudo add-apt-repository ppa:webupd8team/java");
		commands.add("sudo apt-get update");
		commands.add("sudo echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections");
		commands.add("sudo apt-get install -y oracle-java8-installer");
		commands.add("sudo apt-get install -y oracle-java8-set-default");
		
		commands.add("sudo wget http://apache.rediris.es/kafka/0.8.2.1/kafka_2.11-0.8.2.1.tgz");
		commands.add("sudo tar -xvf kafka_2.11-0.8.2.1.tgz");
		commands.add("sudo chmod 777 /home/ubuntu/kafka_2.11-0.8.2.1/config/server.properties");
		commands.add("sudo echo 'port=9092' > /home/ubuntu/kafka_2.11-0.8.2.1/config/server.properties");
		commands.add("sudo echo 'num.network.threads=3' >> /home/ubuntu/kafka_2.11-0.8.2.1/config/server.properties");
		commands.add("sudo echo 'num.io.threads=8' >> /home/ubuntu/kafka_2.11-0.8.2.1/config/server.properties");
		commands.add("sudo echo 'socket.send.buffer.bytes=102400' >> /home/ubuntu/kafka_2.11-0.8.2.1/config/server.properties");
		commands.add("sudo echo 'socket.receive.buffer.bytes=102400' >> /home/ubuntu/kafka_2.11-0.8.2.1/config/server.properties");
		commands.add("sudo echo 'socket.request.max.bytes=104857600' >> /home/ubuntu/kafka_2.11-0.8.2.1/config/server.properties");
		commands.add("sudo echo 'log.dirs=/tmp/kafka-logs' >> /home/ubuntu/kafka_2.11-0.8.2.1/config/server.properties");
		commands.add("sudo echo 'num.partitions=1' >> /home/ubuntu/kafka_2.11-0.8.2.1/config/server.properties");
		commands.add("sudo echo 'num.recovery.threads.per.data.dir=1' >> /home/ubuntu/kafka_2.11-0.8.2.1/config/server.properties");
		commands.add("sudo echo 'log.retention.hours=168' >> /home/ubuntu/kafka_2.11-0.8.2.1/config/server.properties");
		commands.add("sudo echo 'log.segment.bytes=1073741824' >> /home/ubuntu/kafka_2.11-0.8.2.1/config/server.properties");
		commands.add("sudo echo 'log.retention.check.interval.ms=300000' >> /home/ubuntu/kafka_2.11-0.8.2.1/config/server.properties");
		commands.add("sudo echo 'log.cleaner.enable=false' >> /home/ubuntu/kafka_2.11-0.8.2.1/config/server.properties");
		commands.add("sudo echo 'zookeeper.connect=172.31.10.156:2181' >> /home/ubuntu/kafka_2.11-0.8.2.1/config/server.properties");
		commands.add("sudo echo 'zookeeper.connection.timeout.ms=6000' >> /home/ubuntu/kafka_2.11-0.8.2.1/config/server.properties");
		
		//commands.add("sudo sh /home/ubuntu/zookeeper-3.4.6/bin/zkServer.sh start");
		
		
		ArrayList<String> hosts = new ArrayList<String>();
		hosts.add("52.28.108.252");
		hosts.add("52.28.115.33");
		hosts.add("52.28.45.129");
		
		for (int i = 0 ; i < hosts.size() ; i++) {
			
			//commands.remove(commands.size() - 1);
			String brokerId = "broker.id="+i;
			String echoCommand = "sudo echo '" +brokerId+ "' >> /home/ubuntu/kafka_2.11-0.8.2.1/config/server.properties";
			commands.add(echoCommand);
			System.out.println("Calling setup "+ commands.toString());
			setupEnvironment(hosts.get(i), "ubuntu", "");
			start(hosts.get(i), "ubuntu", "");
		}
		
		
	}

	
	public static void setupEnvironment(String host, String user, String sudo_pass) {
		
		try {
			JSch jsch = new JSch();
			jsch.addIdentity("/Users/anuj/ET-SLWA/AWS-Key/ET-SLWA.pem");
//			JFileChooser chooser = new JFileChooser();
//			chooser.setDialogTitle("Choose your privatekey(ex. ~/.ssh/id_dsa)");
//			chooser.setFileHidingEnabled(false);
//			int returnVal = chooser.showOpenDialog(null);
//			if (returnVal == JFileChooser.APPROVE_OPTION) {
//				System.out.println("You chose "
//						+ chooser.getSelectedFile().getAbsolutePath() + ".");
//				//System.out.println(chooser.getSelectedFile().getAbsolutePath());
//				jsch.addIdentity(chooser.getSelectedFile().getAbsolutePath()
//				// , "passphrase"
//				);
//			}

			//String host = "";
//			if (arg.length > 0) {
//				host = arg[0];
//			} else {
//				host = JOptionPane.showInputDialog("Enter username@hostname",
//						System.getProperty("user.name") + "@localhost");
//			}
//			host = JOptionPane.showInputDialog("Enter username@hostname",
//					System.getProperty("user.name") + "@localhost");
//			String user = host.substring(0, host.indexOf('@'));
//			host = host.substring(host.indexOf('@') + 1);

			Session session = jsch.getSession(user, host, 22);

			// username and passphrase will be given via UserInfo interface.
			UserInfo ui = new MyUserInfo();
			session.setUserInfo(ui);
			session.connect();

//			String sudo_pass = null;
//			{
//				JTextField passwordField = (JTextField) new JPasswordField(8);
//				Object[] ob = { passwordField };
//				int result = JOptionPane
//						.showConfirmDialog(null, ob, "Enter password for sudo",
//								JOptionPane.OK_CANCEL_OPTION);
//				if (result != JOptionPane.OK_OPTION) {
//					System.exit(-1);
//				}
//				sudo_pass = passwordField.getText();
//			}

			Channel channel = session.openChannel("exec");
			StringBuffer command = new StringBuffer();
			Iterator<String> commandsItr = commands.iterator();
			while (commandsItr.hasNext()) {
				command = command.append(commandsItr.next()).append(" && ");
			}
			command = command.delete(command.lastIndexOf("&&") - 1, command.length()-1);
			System.out.println(command.toString());
			((ChannelExec) channel).setCommand("sudo -S -p '' " + command.toString());

			InputStream in = channel.getInputStream();
			OutputStream out = channel.getOutputStream();
			((ChannelExec) channel).setErrStream(System.err);

			channel.connect();

			out.write((sudo_pass + "\n").getBytes());
			out.flush();

			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					System.out.print(new String(tmp, 0, i));
				}
				if (channel.isClosed()) {
					System.out.println("exit-status: "
							+ channel.getExitStatus());
//					channel = session.openChannel("exec");
//					command = JOptionPane
//							.showInputDialog("Enter command, execed with sudo",
//									"printenv SUDO_USER");
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
					System.out.println(ee);
				}

			}

			// channel.setInputStream(System.in);
			// channel.setOutputStream(System.out);
			
			
			
//			while (command != "exit") {
//				
//				
//
//			}

			channel.disconnect();
			session.disconnect();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
public static void start(String host, String user, String sudo_pass) {
		
		try {
			JSch jsch = new JSch();
			jsch.addIdentity("/Users/anuj/ET-SLWA/AWS-Key/ET-SLWA.pem");
//			JFileChooser chooser = new JFileChooser();
//			chooser.setDialogTitle("Choose your privatekey(ex. ~/.ssh/id_dsa)");
//			chooser.setFileHidingEnabled(false);
//			int returnVal = chooser.showOpenDialog(null);
//			if (returnVal == JFileChooser.APPROVE_OPTION) {
//				System.out.println("You chose "
//						+ chooser.getSelectedFile().getAbsolutePath() + ".");
//				//System.out.println(chooser.getSelectedFile().getAbsolutePath());
//				jsch.addIdentity(chooser.getSelectedFile().getAbsolutePath()
//				// , "passphrase"
//				);
//			}

			//String host = "";
//			if (arg.length > 0) {
//				host = arg[0];
//			} else {
//				host = JOptionPane.showInputDialog("Enter username@hostname",
//						System.getProperty("user.name") + "@localhost");
//			}
//			host = JOptionPane.showInputDialog("Enter username@hostname",
//					System.getProperty("user.name") + "@localhost");
//			String user = host.substring(0, host.indexOf('@'));
//			host = host.substring(host.indexOf('@') + 1);

			Session session = jsch.getSession(user, host, 22);

			// username and passphrase will be given via UserInfo interface.
			UserInfo ui = new MyUserInfo();
			session.setUserInfo(ui);
			session.connect();

//			String sudo_pass = null;
//			{
//				JTextField passwordField = (JTextField) new JPasswordField(8);
//				Object[] ob = { passwordField };
//				int result = JOptionPane
//						.showConfirmDialog(null, ob, "Enter password for sudo",
//								JOptionPane.OK_CANCEL_OPTION);
//				if (result != JOptionPane.OK_OPTION) {
//					System.exit(-1);
//				}
//				sudo_pass = passwordField.getText();
//			}

			Channel channel = session.openChannel("shell");
			String command = "sudo nohup /home/ubuntu/kafka_2.11-0.8.2.1/bin/kafka-server-start.sh /home/ubuntu/kafka_2.11-0.8.2.1/config/server.properties > /dev/null 2>&1 &";
//			Iterator<String> commandsItr = commands.iterator();
//			while (commandsItr.hasNext()) {
//				command = command.append(commandsItr.next()).append(" && ");
//			}
			//command = 
			System.out.println(command.toString());
			//(channel).setCommand("sudo -S -p '' " + command.toString());

			InputStream in = channel.getInputStream();
			OutputStream out = channel.getOutputStream();
			//((ChannelExec) channel).setErrStream(System.err);

			channel.connect();

			out.write((sudo_pass + "\n").getBytes());
			out.write((command).getBytes());
		    out.write(("\n").getBytes());
			out.flush();
			
			//channel.disconnect();

			byte[] tmp = new byte[1024];
			out.write("exit".getBytes());
			out.write(("\n").getBytes());
			out.flush();
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					System.out.print(new String(tmp, 0, i));
				}
				
				if (channel.isClosed()) {
					System.out.println("exit-status: "
							+ channel.getExitStatus());
//					channel = session.openChannel("exec");
//					command = JOptionPane
//							.showInputDialog("Enter command, execed with sudo",
//									"printenv SUDO_USER");
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
					System.out.println(ee);
				}

			}

			// channel.setInputStream(System.in);
			// channel.setOutputStream(System.out);
			
			
			
//			while (command != "exit") {
//				
//				
//
//			}

			channel.disconnect();
			session.disconnect();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public static class MyUserInfo implements UserInfo, UIKeyboardInteractive {
		public String getPassword() {
			return null;
		}

		public boolean promptYesNo(String str) {
			Object[] options = { "yes", "no" };
			int foo = JOptionPane.showOptionDialog(null, str, "Warning",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
					null, options, options[0]);
			return foo == 0;
		}

		String passphrase;
		JTextField passphraseField = (JTextField) new JPasswordField(20);

		public String getPassphrase() {
			return passphrase;
		}

		public boolean promptPassphrase(String message) {
			Object[] ob = { passphraseField };
			int result = JOptionPane.showConfirmDialog(null, ob, message,
					JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				passphrase = passphraseField.getText();
				return true;
			} else {
				return false;
			}
		}

		public boolean promptPassword(String message) {
			return true;
		}

		public void showMessage(String message) {
			JOptionPane.showMessageDialog(null, message);
		}

		final GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 1, 1,
				GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0);
		private Container panel;

		public String[] promptKeyboardInteractive(String destination,
				String name, String instruction, String[] prompt, boolean[] echo) {
			panel = new JPanel();
			panel.setLayout(new GridBagLayout());

			gbc.weightx = 1.0;
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			gbc.gridx = 0;
			panel.add(new JLabel(instruction), gbc);
			gbc.gridy++;

			gbc.gridwidth = GridBagConstraints.RELATIVE;

			JTextField[] texts = new JTextField[prompt.length];
			for (int i = 0; i < prompt.length; i++) {
				gbc.fill = GridBagConstraints.NONE;
				gbc.gridx = 0;
				gbc.weightx = 1;
				panel.add(new JLabel(prompt[i]), gbc);

				gbc.gridx = 1;
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.weighty = 1;
				if (echo[i]) {
					texts[i] = new JTextField(20);
				} else {
					texts[i] = new JPasswordField(20);
				}
				panel.add(texts[i], gbc);
				gbc.gridy++;
			}

			if (JOptionPane.showConfirmDialog(null, panel, destination + ": "
					+ name, JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {
				String[] response = new String[prompt.length];
				for (int i = 0; i < prompt.length; i++) {
					response[i] = texts[i].getText();
				}
				return response;
			} else {
				return null; // cancel
			}
		}
	}

}
