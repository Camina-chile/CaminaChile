package com.exj.jc.caminachile;

public class Places {
    String idplace;
    String region;
    String provincia;
    String comuna;
    String placeName;
    String commentPlace;
    String image1;
    String image2;
    String image3;
    String image4;
    String lat;
    String lng;
    String placefinal;
    String mediaFile;
    String type_placefb;
    String mailPlace;

    public Places(){

    }


    public Places(String idplace,String region, String provincia,String comuna, String placeName,String commentPlace, String image1,String image2,
                  String image3,String image4,String lat,String lng,String placefinal,String mediaFile,String type_placefb, String mailPlace) {
        this.idplace=idplace;
        this.region=region;
        this.provincia=provincia;
        this.comuna=comuna;
        this.placeName=placeName;
        this.commentPlace=commentPlace;
        this.image1=image1;
        this.image2=image2;
        this.image3=image3;
        this.image4=image4;
        this.lat=lat;
        this.lng=lng;
        this.placefinal=placefinal;
        this.mediaFile=mediaFile;
        this.type_placefb=type_placefb;
        this.mailPlace=mailPlace;


    }


    public String getIdplace() {
        return idplace;
    }

    public String getRegion() {
        return region;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getComuna() {
        return comuna;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getCommentPlace(){
        return commentPlace;
    }

    public String getImage1(){
        return image1;
    }

    public String getImage2(){
        return image3;
    }
    public String getImage3(){
        return image3;
    }
    public String getImage4(){
        return image4;
    }

    public String getLat(){
        return lat;
    }
    public String getLng(){
        return lng;
    }
    public String getPlacefinal(){
        return placefinal;
    }

    public String getMediaFile(){
        return mediaFile;
    }

    public String getType_placefb(){
        return type_placefb;
    }

    public String getMailPlace(){
        return mailPlace;
    }
}
