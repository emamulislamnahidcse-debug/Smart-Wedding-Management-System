public class Guest {

    private String guestId;
    private String guestName;
    private String phone;
    private String address;
    private String gender;
    private String relation;
    private String spouseName;
    private String giftType;
    private String giftDetails;
    private String weddingId;

    public Guest(String guestId,
                 String guestName,
                 String phone,
                 String address,
                 String gender,
                 String relation,
                 String spouseName,
                 String giftType,
                 String giftDetails,
                 String weddingId) {

        this.guestId = guestId;
        this.guestName = guestName;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.relation = relation;
        this.spouseName = spouseName;
        this.giftType = giftType;
        this.giftDetails = giftDetails;
        this.weddingId = weddingId;
    }

    public String getGuestId() {
        return guestId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public String getRelation() {
        return relation;
    }

    public String getSpouseName() {
        return spouseName;
    }

    public String getGiftType() {
        return giftType;
    }

    public String getGiftDetails() {
        return giftDetails;
    }

    public String getWeddingId() {
        return weddingId;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    public void setGiftType(String giftType) {
        this.giftType = giftType;
    }

    public void setGiftDetails(String giftDetails) {
        this.giftDetails = giftDetails;
    }

    public void setWeddingId(String weddingId) {
        this.weddingId = weddingId;
    }
}