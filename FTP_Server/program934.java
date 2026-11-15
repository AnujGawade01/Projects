import java.io.*;
import java.net.*;

class program934
{
    public static void main(String A[])
    {
        try
        {
            ServerSocket serversocket = new ServerSocket(9000);

            System.out.println("-----------------------------------");
            System.out.println("-----Marvellous Server Started-----");
            System.out.println("-----------------------------------");

            // Loop for multiple client requests
            while(true)
            {
                System.out.println("Server is waiting for client request");

                Socket clientsocket = serversocket.accept();

                System.out.println("Client connected successfully");

                // Thread gets created for client
                Thread t = new Thread(() -> HandleClientRequest(clientsocket));
                
                t.start();
            }   // End of while

        }
        catch(Exception e)
        {
            System.out.println("Exception occured : "+e);
        }
    }   // End of main

    public static int ClientCount = 1;

    // Request handler for each client request
    public static void HandleClientRequest(Socket socket)
    {
        System.out.println("New thread gets created for client no : "+ClientCount);
        ClientCount++;

        try
        {
            DataInputStream dis = new DataInputStream(socket.getInputStream());

            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            dos.writeUTF("Connected to Marvellous Server");

            while(true)
            {
                // Read command from client
                String command = dis.readUTF();

                System.out.println("Command received from client : "+command);

                String parts[] = command.split(" ");

                String operation = parts[0].toUpperCase();

                if(operation.equals("QUIT"))
                {
                    if(parts.length != 1)
                    {
                        System.out.println("Usage : QUIT");
                        continue;
                    }
                    dos.writeUTF("Disconnected from server");

                    ClientCount--;

                    break;
                }

                // DONE
                if(operation.equals("GET"))
                {
                    if(parts.length != 2)
                    {
                        System.out.println("Usage : GET <FileName>");
                        continue;
                    }
                    
                    String fileName = parts[1];

                    File file = new File(fileName);

                    if(file.exists() == false || file.isFile() == false)
                    {
                        dos.writeUTF("FILE_NOT_FOUND");

                        continue;
                    }

                    dos.writeUTF("File found");

                    long filesize = file.length();

                    dos.writeLong(filesize);

                    FileInputStream fis = new FileInputStream(file);

                    byte buffer[] = new byte[1024];

                    int bytesread = 0;

                    while((bytesread = fis.read(buffer)) != -1)
                    {
                        dos.write(buffer,0,bytesread);
                    }

                    dos.flush();
                    fis.close();

                    System.out.println("File sent successfully to the client");
                }
                // PUT B.txt (Upload)
                else if(operation.equals("PUT"))
                {
                    if(parts.length != 2)
                    {
                        System.out.println("Usage : PUT <FileName>");
                        continue;
                    }

                    String filename = parts[1];

                    dos.writeUTF("READY");

                    long filesize = dis.readLong();

                    FileOutputStream fos = new FileOutputStream(filename);

                    byte buffer[] = new byte[1024];

                    long received = 0;

                    while(received < filesize)
                    {
                        long remaining = filesize - received;

                        int toread;

                        if(remaining > buffer.length)
                        {
                            toread = buffer.length;
                        }
                        else
                        {
                            toread = (int)remaining;
                        }

                        int bytesread = dis.read(buffer,0,toread);

                        if(bytesread == -1)
                        {
                            break;
                        }

                        fos.write(buffer,0,bytesread);

                        received = received + bytesread;  
                    }
                }
                else if(operation.equals("INFO"))
                {
                    if(parts.length != 2)
                    {
                        System.out.println("Usage : INFO <FileName>");
                        continue;
                    }

                    File file = new File(parts[1]);

                    if(file.exists())
                    {
                        String info = "";
                        info = info + "File name : "+file.getName()+"\n";
                        info = info + "File size : "+file.length()+"\n";
                        info = info + "Readable : "+file.canRead()+"\n";
                        info = info + "Writable : "+file.canWrite()+"\n";

                        dos.writeUTF(info);
                    }
                    else
                    {
                        dos.writeUTF("File does not exist");
                    }
                }
                // Size Demo.txt
                else if(operation.equals("SIZE"))
                {
                    if(parts.length != 2)
                    {
                        System.out.println("Usage : SIZE <FileName>");
                        continue;
                    }

                    File file = new File(parts[1]);

                    if(file.exists() && file.isFile())
                    {
                        dos.writeUTF("File Size is : "+file.length()+" bytes");
                    }
                    else
                    {
                        dos.writeUTF("File does not exist");
                    }

                }
                else if(operation.equals("EXISTS"))
                {
                    if(parts.length != 2)
                    {
                        System.out.println("Usage : EXISTS <FileName>");
                        continue;
                    }

                    File file = new File(parts[1]);

                    if(file.exists())
                    {
                        dos.writeUTF("File exists");
                    }
                    else
                    {
                        dos.writeUTF("File does not exist");
                    }
                }
                // RENAME Demo.txt DemoX.txt
                else if(operation.equals("RENAME"))
                {
                    if(parts.length != 3)
                    {
                        System.out.println("Usage : RENAME <OldFileName> <NewFileName>");
                        continue;
                    }

                    File oldfile = new File(parts[1]);
                    File newfile = new File(parts[2]);

                    if(oldfile.exists() == false)
                    {
                        dos.writeUTF("Source file does not exist");
                    }
                    
                    if(oldfile.renameTo(newfile))
                    {
                        dos.writeUTF("File renamed successfully");
                    }
                    else
                    {
                        dos.writeUTF("Unable to rename file");
                    }
                }
                else if(operation.equals("DELETE"))
                {
                    if(parts.length != 2)
                    {
                        dos.writeUTF("Usage : DELETE <FileName>");
                        continue;
                    }

                    File file = new File(parts[1]);

                    if(file.exists() == false)
                    {
                        dos.writeUTF("There is o such file");
                        continue;
                    }

                    if(file.delete())
                    {
                        dos.writeUTF("File deleted successfully");
                    }
                    else
                    {
                        dos.writeUTF("Unable to delete the file");
                    }

                }
                else if(operation.equals("LIST"))
                {
                    if(parts.length != 1)
                    {
                        dos.writeUTF("Usage : LIST");
                        continue;
                    }

                    File folder = new File(".");

                    File files[] = folder.listFiles();

                    String result = "";

                    if(files != null)
                    {
                        for( File f : files )
                        {
                            if(f.isFile())
                            {
                                result = result + f.getName() +"\n";
                            }
                        }
                    }

                    if(files.length == 0)
                    {
                        result = "No files available";
                    }

                    dos.writeUTF(result);
                    
                }
                else
                {
                    dos.writeUTF("Invalid operation");
                }

            }   // End of while

            socket.close();
            dis.close();
            dos.close();

            System.out.println("Client disconnected");

        }   // End of try

        catch(Exception e)
        {
            System.out.println("Exception occured : "+e);
        }
    }   // End of HandleClientRequest() method

}   // End of class