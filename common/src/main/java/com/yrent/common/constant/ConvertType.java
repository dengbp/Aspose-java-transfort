package com.yrent.common.constant;

/**
 * @author dengbp
 * @ClassName ConverType
 * @Description TODO
 * @date 4/17/22 3:33 AM
 */
public enum ConvertType {

    /** */
    word_to_pdf((char)1,"word转pdf"),
    /** */
    pdf_to_word((char)2,"pdf转word"),
    /** */
    excel_to_pdf((char)3,"excel转pdf"),
    /** */
    pdf_to_excel((char)4,"pdf转excel"),
    /** */
    ppt_to_pdf((char)5,"ppt转pdf"),
    /** */
    pdf_to_ppt((char)6,"pdf转ppt"),
    /** */
    pic_to_pdf((char)7,"图片转pdf"),
    /** */
    pdf_to_jpg((char)8,"pdf转jpg"),
    /** */
   // png_to_pdf((char)9,"png转pdf"),
    /** */
    pdf_to_png((char)10,"pdf转png"),
    /** */
    docx_to_pdf((char)11,"docx转pdf"),
    /** */
    pdf_to_docx((char)12,"pdf转docx"),
    /** */
    odt_to_pdf((char)13,"odt转pdf"),
    /** */
    doc_to_pdf((char)14,"doc转pdf");



    ConvertType(char code, String des) {
        this.code = code;
        this.des = des;
    }

    private final char code;
    private final String des;

    public static ConvertType getByCode(char code){
        for(ConvertType e : ConvertType.values()){
            if(e.getCode()==code){
                return e;
            }
        }
        return null;
    }

    public char getCode() {
        return code;
    }

    public String getDes() {
        return des;
    }
}
