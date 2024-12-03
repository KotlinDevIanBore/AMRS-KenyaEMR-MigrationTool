package ampath.co.ke.amrs_kenyaemr.tasks;

public class Mappers {
    public static String identifers(String uuid) {
        String kenyendidentifer="";
        if(uuid.equals("58a47054-1359-11df-a1f1-0026b9348838")){
            //IDNO
            kenyendidentifer="49af6cdc-7968-4abb-bf46-de10d7f4859f";
        } else if(uuid.equals("58a4732e-1359-11df-a1f1-0026b9348838")){
            //AMRSID
            kenyendidentifer="b4d66522-11fc-45c7-83e3-39a1af21ae0d";}
        else if(uuid.equals("f2d6ff1a-8440-4d35-a150-1d4b5a930c5e")){
            //CCC Number
            kenyendidentifer="05ee9cf4-7242-4a17-b4d4-00f707265c8a";}
        else if(uuid.equals("cba702b9-4664-4b43-83f1-9ab473cbd64d")){
            //NUPI
            kenyendidentifer="f85081e2-b4be-4e48-b3a4-7994b69bb101";}
        else if(uuid.equals("91099b3f-69be-4607-a309-bd358d85af46")){
            //Prep
            kenyendidentifer="ac64e5cb-e3e2-4efa-9060-0dd715a843a1";}
        else if(uuid.equals("ead42a8f-203e-4b11-a942-df03a460d617")){
            //HEI
            kenyendidentifer="0691f522-dd67-4eeb-92c8-af5083baf338";}
        else if(uuid.equals("d8ee3b8c-a8fc-4d6b-af6a-9423be5f8906")){
            //TB
            kenyendidentifer="d8ee3b8c-a8fc-4d6b-af6a-9423be5f8906";}
        else if(uuid.equals("ace5f7c7-c5f4-4e77-a077-5588a682a0d6")){
            //CPMIS
            kenyendidentifer="5065ae70-0b61-11ea-8d71-362b9e155667";}
        else if(uuid.equals("7924e13b-131a-4da8-8efa-e294184a1b0d")){
            //BirthCertificateBNo
            kenyendidentifer="68449e5a-8829-44dd-bfef-c9c8cf2cb9b2";}
        return kenyendidentifer;
    }
    public static String programs(String uuid) {
        String programs="";
        if(uuid.equals("58a47054-1359-11df-a1f1-0026b9348838")||
                uuid.equals("781d85b0-1359-11df-a1f1-0026b9348838")||
                uuid.equals("c6bf3625-de80-4a88-a913-38273e300a55")||
                uuid.equals("96ba279b-b23b-4e78-aba9-dcbd46a96b7b")||
                uuid.equals(" 7843bd0e-2309-44dd-963b-1380789c372c")||
                uuid.equals("3aebf7f4-bb06-40e9-9eb4-0a6ea458bc63")||
                uuid.equals("334c9e98-173f-4454-a8ce-f80b20b7fdf0")
        ){
            //HIV
            programs="dfdc6d40-2f2f-463d-ba90-cc97350441a8";
        }
        else if(uuid.equals("80839137-9711-483f-a239-dfd383d020f6")){
            //MCH - Mother Services
            programs="b5d9e05f-f5ab-4612-98dd-adb75438ed34";}
        else if(uuid.equals("01948855-8491-4164-b54f-9b16d174d93e")||(uuid.equals("d2552058-d7bd-47c6-aed1-480a4308027a"))||(uuid.equals("52aeb285-fb18-455b-893e-3e53ccc77ceb"))||(uuid.equals("781d897a-1359-11df-a1f1-0026b9348838"))){
            //MCH - Mother Services
            programs="c2ecdf11-97cd-432a-a971-cfd9bd296b83";}
        else if(uuid.equals("91099b3f-69be-4607-a309-bd358d85af46")){
            //TB
            programs="9f144a34-3a4a-44a9-8486-6b7af6cc64f6";}
        else if(uuid.equals("ead42a8f-203e-4b11-a942-df03a460d617")){
            //TPT
            programs="335517a1-04bc-438b-9843-1ba49fb7fcd9";}
        else if(uuid.equals("781d8768-1359-11df-a1f1-0026b9348838")){
            //OVC
            programs="6eda83f0-09d9-11ea-8d71-362b9e155667";}
        else if(uuid.equals("203571d6-a4f2-4953-9e8b-e1105e2340f5")){
            //OTZ
            programs="24d05d30-0488-11ea-8d71-362b9e155667";}
        else if(uuid.equals("c19aec66-1a40-4588-9b03-b6be55a8dd1d")){
            //Prep
            programs="214cad1c-bb62-4d8e-b927-810a046daf62";}
        else if(uuid.equals("ace5f7c7-c5f4-4e77-a077-5588a682a0d6")){
            //Key Pop
            programs="7447305a-18a7-11e9-ab14-d663bd873d93";}
        else if(uuid.equals("fc15ac01-5381-4854-bf5e-917c907aa77f")){
            //NCD
            programs="ffee43c4-9ccd-4e55-8a70-93194e7fafc6";}
        else if(uuid.equals("a8e7c30d-6d2f-401c-bb52-d4433689a36b")){
            //HEI - MCH Child
            programs="c2ecdf11-97cd-432a-a971-cfd9bd296b83";}

        return programs;
    }

    }
