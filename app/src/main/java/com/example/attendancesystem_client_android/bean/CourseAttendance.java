package com.example.attendancesystem_client_android.bean;

public class CourseAttendance {
    private String 出勤;
    private String 时间;
    private String 序号;
    private String 病假;
    private String 事假;
    private String 缺勤;

    @Override
    public String toString() {
        return "CourseAttendance{" +
                "出勤='" + 出勤 + '\'' +
                ", 时间='" + 时间 + '\'' +
                ", 序号='" + 序号 + '\'' +
                ", 病假='" + 病假 + '\'' +
                ", 事假='" + 事假 + '\'' +
                ", 缺勤='" + 缺勤 + '\'' +
                ", 应到='" + 应到 + '\'' +
                '}';
    }

    private String 应到;

    public String get病假() {
        return 病假;
    }

    public void set病假(String 病假) {
        this.病假 = 病假;
    }

    public CourseAttendance() {
    }

    public String get出勤() {
        return 出勤;
    }

    public void set出勤(String 出勤) {
        this.出勤 = 出勤;
    }

    public String get时间() {
        return 时间;
    }

    public void set时间(String 时间) {
        this.时间 = 时间;
    }

    public String get序号() {
        return 序号;
    }

    public void set序号(String 序号) {
        this.序号 = 序号;
    }

    public String get事假() {
        return 事假;
    }

    public void set事假(String 事假) {
        this.事假 = 事假;
    }

    public String get缺勤() {
        return 缺勤;
    }

    public void set缺勤(String 缺勤) {
        this.缺勤 = 缺勤;
    }

    public String getInformation(){
        return  序号 + " 时间:" + " 出勤:" +出勤 + " 病假:" + 病假 + " 事假:" + 事假 + " 缺勤:" + 缺勤 + " 应到:" + 应到;
    }

    public String get应到() {
        return 应到;
    }

    public void set应到(String 应到) {
        this.应到 = 应到;
    }
}
