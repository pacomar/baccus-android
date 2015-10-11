/**
 * Created by Paco on 26/10/14.
 */
package biz.agbo.baccus.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class Wine implements Serializable {
    private String mId = null;
    private String mName = null;
    private String mType = null;
    private Bitmap mPhoto = null;
    private String mPhotoURL = null;
    private String mWineCompanyWeb = null;
    private String mNotes = null;
    private String mOrigin = null;
    private int mRating = 0; //0 to 5
    private String mWineCompanyName = null;
    private List<String> mGrapes = new LinkedList<String>();

    public Wine(String id, String name, String type, String photoURL, String wineCompanyWeb, String notes, String origin, int rating, String wineCompanyName, List<String> grapes) {
        mId = id;
        mName = name;
        mType = type;
        mPhotoURL = photoURL;
        mWineCompanyWeb = wineCompanyWeb;
        mNotes = notes;
        mOrigin = origin;
        mRating = rating;
        mWineCompanyName = wineCompanyName;
        mGrapes = grapes;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getPhotoURL() {
        return mPhotoURL;
    }

    public void setPhotoURL(String photoURL) {
        mPhotoURL = photoURL;
    }

    public Bitmap getPhoto(Context context) {
        if (mPhoto == null){
            mPhoto = getBitMapFromUrl(getPhotoURL(), context);
        }
        return mPhoto;
    }

    private Bitmap getBitMapFromUrl(String photoURL, Context context) {
        File imageFile = new File(context.getCacheDir(), getId());
        if (imageFile.exists()){
            return BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        }else{
            InputStream in = null;
            try {
                in = new URL(photoURL).openStream();
                Bitmap bmp = BitmapFactory.decodeStream(in);
                FileOutputStream fos = new FileOutputStream(imageFile);
                bmp.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.close();
                return bmp;
            } catch (Exception ex) {
                Log.e("Baccus", "Error download image", ex);
                return null;
            }
            finally{
                try {
                    if (in != null) {
                        in.close();
                    }
                }catch (IOException e) {

                }
            }
        }
    }

    public void setPhoto(Bitmap photo) {
        mPhoto = photo;
    }

    public String getWineCompanyWeb() {
        return mWineCompanyWeb;
    }

    public void setWineCompanyWeb(String wineCompanyWeb) {
        mWineCompanyWeb = wineCompanyWeb;
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String notes) {
        mNotes = notes;
    }

    public String getOrigin() {
        return mOrigin;
    }

    public void setOrigin(String origin) {
        mOrigin = origin;
    }

    public int getRating() {
        return mRating;
    }

    public void setRating(int rating) {
        mRating = rating;
    }

    public String getWineCompanyName() {
        return mWineCompanyName;
    }

    public void setWineCompanyName(String wineCompanyName) {
        mWineCompanyName = wineCompanyName;
    }

    public List<String> getGrapes() {
        return mGrapes;
    }

    public void setGrapes(List<String> grapes) {
        mGrapes = grapes;
    }

    public String getGrape(int index){
        return this.mGrapes.get(index);
    }

    public int getGrapesCount(){
        return this.mGrapes.size();
    }

    //Añadir uva
    public void addGrape(String grape){
        this.mGrapes.add(grape);
    }

    @Override
    public String toString() {
        return getName();
    }
}
