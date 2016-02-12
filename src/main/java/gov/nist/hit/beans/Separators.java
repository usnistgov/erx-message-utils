package gov.nist.hit.beans;

/**
 * Created by mcl1 on 1/20/16.
 */
public class Separators {
    private String fieldSeparator;
    private String componentSeparator;
    private String repetitionSeparator;
    private String escapeCharacter;
    private String delimitationCharacter;
    private String lineSeparator;

    public Separators(String unaSegment) {
        parse(unaSegment);
    }

    public void parse(String unaSegment){
        //:+./*'
        this.componentSeparator = ""+unaSegment.charAt(3);
        this.fieldSeparator = ""+unaSegment.charAt(4);
        this.delimitationCharacter = ""+unaSegment.charAt(5);
        this.escapeCharacter = ""+unaSegment.charAt(6);
        this.repetitionSeparator = ""+unaSegment.charAt(7);
        this.lineSeparator = ""+unaSegment.charAt(8);
    }

    public String getLineSeparator() {
        return lineSeparator;
    }

    public String getFieldSeparator() {
        return fieldSeparator;
    }

    public String getComponentSeparator() {
        return componentSeparator;
    }

    public String getRepetitionSeparator() {
        return repetitionSeparator;
    }

    public String getEscapeCharacter() {
        return escapeCharacter;
    }

    public String getDelimitationCharacter() {
        return delimitationCharacter;
    }

}
