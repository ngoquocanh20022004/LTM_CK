package Server_2;

import java.io.*;
import java.net.*;
import java.util.*;

public class ConvertServer {
    private static final int PORT = 7749;
    private static final String PROCESSED_DIR = "results";
    private static final String QUEUE_DIR = "queues";
    
    private static final int MAX_QUEUE_SIZE = 20;
    private static final Queue<ResponseHandler> resQueue = new LinkedList<>();
    private static final int THREAD_COUNT = 3; //xuly 3 file cùng lúc
    
    
    
    public static void main(String[] args) {
        setupDirectories();

        for (int i = 0; i < THREAD_COUNT; i++) {
            new ProcessingFile().start();
        }

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
        	System.out.println("Convert Server is running on port " + PORT);
            while (true) {
                try {
                	Socket clientSocket = serverSocket.accept();
					ResponseHandler res = new ResponseHandler(clientSocket);
					if (resQueue.size() >= MAX_QUEUE_SIZE) {
						res.sendResponse("ERROR: Server is busy, please try again later");
					} else {
						synchronized (resQueue) {
	                        resQueue.add(res);
	                        resQueue.notify();
	                    }
					}
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setupDirectories() {
        File processedFolder = new File(PROCESSED_DIR);
        File queueFolder = new File(QUEUE_DIR);
        if (!processedFolder.exists()) processedFolder.mkdirs();
        if (!queueFolder.exists()) queueFolder.mkdirs();
    }

    private static String processFile(File mp4File, int accountId) {
        try {
            String mp3FileName = mp4File.getName().replace(".mp4", "_converted.mp3");
            File accountDir = new File(PROCESSED_DIR, String.valueOf(accountId));
            if (!accountDir.exists()) accountDir.mkdirs();
            File mp3File = new File(accountDir, mp3FileName);

			if (mp3File.exists()) {
				mp3File.delete();
			}
            
            ProcessBuilder processBuilder = new ProcessBuilder("ffmpeg", "-i",
                    mp4File.getAbsolutePath(), mp3File.getAbsolutePath());
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            }

            if (process.waitFor() == 0) {
                System.out.println("Handled file: " + mp4File.getName());
                mp4File.delete();
                return mp3FileName;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static class ProcessingFile extends Thread { //Thread để xử lý file, không bị block main thread
    	@Override
        public void run() {
            while (true) {
                ResponseHandler res = null;
                try {
                    synchronized (resQueue) {
                        while (resQueue.isEmpty()) {
                            resQueue.wait();
                        }
                        res = resQueue.poll();
                    }
                    
                    if (res != null) {
                        File mp4File = new File(QUEUE_DIR, res.getMp4File());
                        int accountId = res.getAccountId();
                        String mp3File = processFile(mp4File, accountId);
						if (mp3File != null) {
							res.sendResponse(mp3File);
						}
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    static class ResponseHandler {
    	Socket soc;
    	PrintWriter out;
    	BufferedReader in;
    	String mp4File;
    	int accountId;
    	
		public ResponseHandler(Socket soc) {
			this.soc = soc;
			try {
				out = new PrintWriter(soc.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
				String receiveData = receiveResponse();
				String[] data = receiveData.split(",");
				mp4File = data[0];
				accountId = Integer.parseInt(data[1]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void sendResponse(String response) {
			try {
                out.println(response);
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
			} finally {
				close();
			}
		}
		
		public String receiveResponse() throws IOException {
			return in.readLine();
		}
		
		public String getMp4File() {
			return mp4File;
		}
		
		public int getAccountId() {
            return accountId;
        }
		
		private void close() {
			try {
                out.close();
                in.close();
                soc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
		}
    }
}
