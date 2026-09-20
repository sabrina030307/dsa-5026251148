package lw01.prelab;

public class MonoPrint extends PrintJob {

    public MonoPrint(String name, int pages) {
        super(name, pages);
    }

    @Override
    public String label() {
        return "MONO";
    }

    @Override
    public int calculateCharge() {
        return getPages() * 500;
    }
}

