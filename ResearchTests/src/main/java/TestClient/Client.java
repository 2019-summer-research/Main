package TestClient;

import api.RequestManager;
import api.endpoint.Faces.FacesAPIMethod;
import api.endpoint.Faces.FacesResponseElement;
import api.endpoint.FacialDetection.FaceDetectionApiMethod;
import api.endpoint.FacialDetection.FaceDetectionResponseElement;
import api.endpoint.Identify.IdentifyApiMethod;
import api.endpoint.Identify.IdentifyResponseElement;
import api.endpoint.Person.PersonAPIMethod;
import api.endpoint.Person.PersonResponseElement;
import api.endpoint.PersonGroup.PersonGroupAPIMethod;
import api.endpoint.PersonGroup.PersonGroupResponseElement;
import api.endpoint.PersonInfo.PersonInfoAPIMethod;
import api.endpoint.PersonInfo.PersonInfoResponseElement;
import api.endpoint.Train.TrainAPIMethod;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;


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
               // System.out.println(element.getError());
                PersonID = element.GetID();
                //System.out.println(Directory + " ID: " +PersonID);
                listFilesForFolder(fileEntry,GroupID,Directory,PersonID);
            } else {
                //System.out.println(Directory + " : " + fileEntry.getName());
                FacesAPIMethod face = new FacesAPIMethod(ConvertImage(fileEntry),GroupID,PersonID);
                FacesResponseElement element = RequestManager.getInstance().makeApiRequest(face);
                //System.out.println(element.getError());
                //System.out.println(element.getPersistedFaceId());
            }
        }
    }


    public static void main(String[] args) {

        int NewDataSet = JOptionPane.showConfirmDialog(null,"Add new class?","Class Creation",JOptionPane.YES_NO_OPTION);


        // 0 = yes
        // 1 = no
        String GroupName = "";
        String GroupID = "";
        File TestFace = null;
        File rootDirectory = null;

        if(NewDataSet == 0) {

            // Open up a JFileChooser to select the dataset directory.
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            // Ensure that we're only selecting directories. Disabling the 'all files' option
            jfc.setDialogTitle("Select your dataset root directory");
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jfc.setAcceptAllFileFilterUsed(false);

            int returnValue = jfc.showOpenDialog(null);

            // Set the class directory to be the one that the user has selected
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                rootDirectory = jfc.getSelectedFile();
            } else {
                // There was an error with choosing a directory. Throw an exception.
                System.err.println("PANIC");
            }
            GroupName = rootDirectory.getName().toLowerCase();
            GroupID = rootDirectory.getName().toLowerCase();
            //***************************TESTING GROUP*****************
            //        GroupID = "facestest";
            PersonGroupAPIMethod PG = new PersonGroupAPIMethod(GroupID,GroupName,"");
            PersonGroupResponseElement element = RequestManager.getInstance().makeApiRequest(PG);
            System.out.println(element.getError());
            listFilesForFolder(rootDirectory,GroupID,rootDirectory.getName(),"-1");
            TrainAPIMethod TAPI = new TrainAPIMethod(GroupID);
        }
        else
        {

           String s = (String)JOptionPane.showInputDialog(null,"Enter Group ID","Group ID Entry");
            if ((s != null) && (s.length() > 0)) {
                GroupID = s.toLowerCase();
            }
            else{
                System.err.println("Error No Entered Group ID");
                return;
            }
        }

        int TestModel = JOptionPane.showConfirmDialog(null,"Test Model With Image?","Model Test",JOptionPane.YES_NO_OPTION);


        if(TestModel == 0) {
            JFileChooser jfc2 = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            jfc2.setDialogTitle("Select your test image");
            int returnValue2 = jfc2.showOpenDialog(null);

            if (returnValue2 == JFileChooser.APPROVE_OPTION) {
                TestFace = jfc2.getSelectedFile();
            } else {
                // There was an error with choosing a directory. Throw an exception.
                System.err.println("PANIC");
            }
            FaceDetectionApiMethod FD = new FaceDetectionApiMethod(ConvertImage(TestFace));
            ArrayList<FaceDetectionResponseElement> elementfd = RequestManager.getInstance().makeApiRequest(FD);
            IdentifyApiMethod ID = new IdentifyApiMethod(elementfd.get(0).getFaceId(), GroupID);
            ArrayList<IdentifyResponseElement> IDR = RequestManager.getInstance().makeApiRequest(ID);
            PersonInfoAPIMethod PI = new PersonInfoAPIMethod(IDR.get(0).getID(), GroupID);
            PersonInfoResponseElement PIelement = RequestManager.getInstance().makeApiRequest(PI);
            JOptionPane.showMessageDialog(null,("Person Guess: " + PIelement.getName()+"\nConfidence: " + IDR.get(0).getConfidence()),"Ident Output",JOptionPane.PLAIN_MESSAGE);
        }
    }
    
    
    
    



}
