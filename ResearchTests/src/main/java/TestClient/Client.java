package TestClient;

import api.RequestManager;
import api.endpoint.Faces.FacesAPIMethod;
import api.endpoint.Faces.FacesResponseElement;
import api.endpoint.FacialDetection.FaceDetectionApiMethod;
import api.endpoint.Person.PersonAPIMethod;
import api.endpoint.Person.PersonResponseElement;
import api.endpoint.PersonGroup.PersonGroupAPIMethod;
import api.endpoint.PersonGroup.PersonGroupResponseElement;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class Client {



    public static byte[] ConvertImage(File fi)
    {
        byte[] fileContent = new byte[0];
        try {
            fileContent = Files.readAllBytes(fi.toPath());

    } catch (IOException e) {
        e.printStackTrace();
    }
        return fileContent;
    }




    public static void listFilesForFolder(final File folder,String GroupID,String Directory,String PersonID) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                Directory = fileEntry.getName();

                PersonAPIMethod P = new PersonAPIMethod(GroupID,fileEntry.getName(),"");
                PersonResponseElement element = RequestManager.getInstance().makeApiRequest(P);
                System.out.println(element.getError());
                PersonID = element.GetID();
                System.out.println(Directory + " ID: " +PersonID);
                listFilesForFolder(fileEntry,GroupID,Directory,PersonID);
            } else {
                System.out.println(Directory + " : " + fileEntry.getName());
                FacesAPIMethod face = new FacesAPIMethod(ConvertImage(fileEntry),GroupID,PersonID);
                FacesResponseElement element = RequestManager.getInstance().makeApiRequest(face);
                System.out.println(element.getError());
                System.out.println(element.getPersistedFaceId());
            }
        }
    }


    public static void main(String[] args) {
        String GroupName = "";
        String GroupID = "";
        Scanner sc = new Scanner(System.in);
        System.out.print("Please Enter Class Name(No spaces): ");
        GroupName = sc.next();
        System.out.print("Please Enter Group ID(All lowercase no spaces): ");
        GroupID = sc.next();

       // PersonGroupAPIMethod PG = new PersonGroupAPIMethod("grouptest","grouptest","");
       // PersonGroupResponseElement element = RequestManager.getInstance().makeApiRequest(PG);
        //System.out.println(element.getError());
        System.out.print("Please Enter Path to Dataset Folder: ");
        String DataSet ="";
        DataSet = sc.next();


        File fi = new File(DataSet);

        listFilesForFolder(fi,"grouptest",DataSet,"-1");






    }
    
    
    
    



}
