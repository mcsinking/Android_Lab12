package com.android.mcsin.lab12;

import android.graphics.Bitmap;

/**
 * Created by 984724 on 12/9/2015.
 */
public class bullet {

     public bullet(Bitmap bitmap, float bulletX, float bulletY, boolean bulletYActivy) {
          this.bitmap = bitmap;
          this.bulletX = bulletX;
          this.bulletY = bulletY;
          this.bulletYActivy = bulletYActivy;
     }

     private Bitmap bitmap;
     private float bulletX;
     private float bulletY;
     private boolean bulletYActivy;



     public float getBulletX() {
          return bulletX;
     }

     public Bitmap getBitmap() {
          return bitmap;
     }

     public void setBitmap(Bitmap bitmap) {
          this.bitmap = bitmap;
     }

     public boolean isBulletYActivy() {
          return bulletYActivy;
     }

     public void setBulletYActivy(boolean bulletYActivy) {
          this.bulletYActivy = bulletYActivy;
     }

     public void setBulletX(float bulletX) {
          this.bulletX = bulletX;
     }

     public float getBulletY() {
          return bulletY;
     }

     public void setBulletY(float bulletY) {
          this.bulletY = bulletY;
     }
}
