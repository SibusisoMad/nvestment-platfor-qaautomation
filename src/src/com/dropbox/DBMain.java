// <copyright file="DBMain.java" company="Atom Consulting Services Ltd">
// Copyright (c) Atom Consulting Services Ltd. All rights reserved.
// </copyright>
package src.com.dropbox;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.sharing.RequestedVisibility;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import com.dropbox.core.v2.sharing.SharedLinkSettings;
import com.dropbox.core.v2.users.FullAccount;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

/*
 * This class is used to upload the screenshot image to dropbox account and which furthur used in reports
 */
public class DBMain {
	public static ArrayList<String> shareurl = new ArrayList<String>();
	private static final String ATOM_ACCESS_TOKEN = "3mVFd3mOAQMAAAAAAAAAAWSbuEmJpGQLqUXYOi7yF5YTWDtDT1wyU-8akZigtdM2";
/*
 * Upload "Generated image" to Dropbox, Get files and folder metadata from Dropbox root directory and set the folder property as public.
 */
    public void DBMain(String imagePath) throws  IOException, Exception, DbxException {
        DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");
        DbxClientV2 client = new DbxClientV2(config, ATOM_ACCESS_TOKEN);
        FullAccount account = client.users().getCurrentAccount();
        
         ListFolderResult result = client.files().listFolder("");
        while (true) {
            for (Metadata metadata : result.getEntries()) {
                System.out.println(metadata.getPathLower());
            }
            if (!result.getHasMore()) {
                break;
            }
            result = client.files().listFolderContinue(result.getCursor());
        }
     
       
        try (InputStream in = new FileInputStream(imagePath)) {
            FileMetadata metadata = client.files().uploadBuilder("/"+imagePath).uploadAndFinish(in);
            
            ListFolderResult result1 = client.files().listFolder("");
            while (true) {
                for (Metadata metadata1 : result1.getEntries()) {
                   // System.out.println(metadata1.getPathLower());
                }
                if (!result1.getHasMore()) {
                    break;
                }
                result1 = client.files().listFolderContinue(result1.getCursor());
            }
            SharedLinkMetadata slm = client.sharing().createSharedLinkWithSettings("" + "/" + imagePath, SharedLinkSettings.newBuilder().withRequestedVisibility(RequestedVisibility.PUBLIC).build());
            String tempurl = slm.getUrl();
            shareurl.add(tempurl) ;
           
        }
    }
}