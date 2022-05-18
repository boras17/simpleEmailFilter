package models.saves;

public enum SavingBy {
    TITLE("saving.by.title"),
    AUTHOR("saving.by.author"),
    CONTENT("saving.by.content");

    private final String name;

    SavingBy(String name){
        this.name = name;
    }

    public String getDisplayValue(){
        return this.name;
    }
}