package com.wisnu_krn.mvvmbelajar;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;
import android.support.annotation.DrawableRes;

/**
 * Created by private on 02/12/2016.
 */

public class GotCharacter implements Parcelable, BaseColumns {

    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String THUMB_URL = "thumb_url";
    public static final String FULL_URL = "full_url";
    public static final String HOUSE = "house";
    public static final String ALIVE = "alive";
    public static final String HOUSE_RES_ID = "house_res_id";
    public static final String DESCRIPTION = "description";
    public static final String[] ALL_COLS = {_ID, FIRST_NAME, LAST_NAME, ALIVE, THUMB_URL, FULL_URL, HOUSE, HOUSE_RES_ID, DESCRIPTION};

    public final int id;
    public final String firstName;
    public final String lastName;
    public final String thumbUrl;
    public final boolean alive;
    public final String fullUrl;
    public final int houseResId;
    public final String house;
    public final String description;

    public GotCharacter(String firstName, String lastName, String fullUrl, boolean alive, String house, @DrawableRes int houseResId, String description, String thumbUrl) {
        this(0, firstName, lastName, thumbUrl, fullUrl, alive, house, houseResId, description);
    }

    public GotCharacter(int id, String firstName, String lastName, String thumbUrl, String fullUrl, boolean alive, String house, int houseResId, String description) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.thumbUrl = thumbUrl;
        this.alive = alive;
        this.fullUrl = fullUrl;
        this.houseResId = houseResId;
        this.house = house;
        this.description = description;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.thumbUrl);
        dest.writeByte(alive ? (byte) 1 : (byte) 0);
        dest.writeString(this.fullUrl);
        dest.writeInt(this.houseResId);
        dest.writeString(this.house);
        dest.writeString(this.description);
    }

    protected GotCharacter(Parcel in) {
        this.id = in.readInt();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.thumbUrl = in.readString();
        this.alive = in.readByte() != 0;
        this.fullUrl = in.readString();
        this.houseResId = in.readInt();
        this.house = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<GotCharacter> CREATOR = new Parcelable.Creator<GotCharacter>() {
        public GotCharacter createFromParcel(Parcel source) {
            return new GotCharacter(source);
        }

        public GotCharacter[] newArray(int size) {
            return new GotCharacter[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GotCharacter that = (GotCharacter) o;

        if (id != that.id) return false;
        if (alive != that.alive) return false;
        if (houseResId != that.houseResId) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null)
            return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null)
            return false;
        if (thumbUrl != null ? !thumbUrl.equals(that.thumbUrl) : that.thumbUrl != null)
            return false;
        if (fullUrl != null ? !fullUrl.equals(that.fullUrl) : that.fullUrl != null) return false;
        if (house != null ? !house.equals(that.house) : that.house != null) return false;
        return !(description != null ? !description.equals(that.description) : that.description != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (thumbUrl != null ? thumbUrl.hashCode() : 0);
        result = 31 * result + (alive ? 1 : 0);
        result = 31 * result + (fullUrl != null ? fullUrl.hashCode() : 0);
        result = 31 * result + houseResId;
        result = 31 * result + (house != null ? house.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GotCharacter{");
        sb.append("id=").append(id);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", thumbUrl='").append(thumbUrl).append('\'');
        sb.append(", alive=").append(alive);
        sb.append(", fullUrl='").append(fullUrl).append('\'');
        sb.append(", houseResId=").append(houseResId);
        sb.append(", house='").append(house).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }

}

