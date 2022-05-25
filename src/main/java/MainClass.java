import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;

import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.*;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainClass {
    public static void main(String[] args) throws IOException {

        PDDocument document=new PDDocument();
        PDPage firstPage=new PDPage(PDRectangle.A4);
        document.addPage(firstPage);

        String name="Vishal Kumar";
        String callNo="+91-9999978760";

        Format d_format=new SimpleDateFormat("dd/MM/YYYY");
        Format t_format=new SimpleDateFormat("HH:mm");

        int pageWidth=(int) firstPage.getTrimBox().getWidth();
        int pageHeight=(int) firstPage.getTrimBox().getHeight();

        PDPageContentStream contentStream=new PDPageContentStream(document,firstPage);
        MyTextClass myTextClass=new MyTextClass(document,contentStream);

        PDFont font= PDType1Font.HELVETICA;
        PDFont italicFont=PDType1Font.HELVETICA_OBLIQUE;

        PDImageXObject headImage=PDImageXObject.createFromFile("D:\\PDFBoxTesting\\src\\main\\resources\\img\\Indian Tadka head.png",document);
        contentStream.drawImage(headImage,0,pageHeight-235,pageWidth,239);

        String[] contactDetails=new String[]{"8299308385","9565176368"};
        myTextClass.addMultiLineText(contactDetails,18,(int)(pageWidth-font.getStringWidth("8299308385")/1000*15-10),pageHeight-25,font,15,Color.BLACK);

        myTextClass.addSingleLineText("INDIAN TADKA",25,pageHeight-150,font,40,Color.BLACK);

        myTextClass.addSingleLineText("Customer Name:"+name,25,pageHeight-250,font,16,Color.BLACK);

        myTextClass.addSingleLineText("Mo. No."+callNo,25,pageHeight-274,font,16,Color.BLACK);

        String invoiceNo="Invoice #2536";
        float textWidth= myTextClass.getTextWidth(invoiceNo,font,16);
        myTextClass.addSingleLineText(invoiceNo,(int)(pageWidth-25-textWidth),pageHeight-250,font,16,Color.BLACK);

        String date=d_format.format(new Date());
        float dateTextWidth= myTextClass.getTextWidth("Date:"+date,font,16);
        myTextClass.addSingleLineText("Date: "+date,(int)(pageWidth-25-dateTextWidth),pageHeight-274,font,16,Color.BLACK);

        String time=t_format.format(new Date());
        float timeTextWidth= myTextClass.getTextWidth("Time:"+time,font,16);
        myTextClass.addSingleLineText("Time: "+time,(int)(pageWidth-25-timeTextWidth),pageHeight-298,font,16,Color.BLACK);

        MyTableClass myTable=new MyTableClass(document,contentStream);
        int[] cellWidths={70,160,120,90,100};
        myTable.setTable(cellWidths,30,25,pageHeight-350);
        myTable.setTableFont(font,16,Color.BLACK);

        Color tableHeadColor=new Color(240,93,11);
        Color tableBodyColor=new Color(219,218,198);

        myTable.addCell("S.No.",tableHeadColor);
        myTable.addCell("Items",tableHeadColor);
        myTable.addCell("Price",tableHeadColor);
        myTable.addCell("Qty",tableHeadColor);
        myTable.addCell("Total",tableHeadColor);

        myTable.addCell("1.",tableBodyColor);
        myTable.addCell("Masala Dosa",tableBodyColor);
        myTable.addCell("120",tableBodyColor);
        myTable.addCell("2",tableBodyColor);
        myTable.addCell("240",tableBodyColor);

        myTable.addCell("2.",tableBodyColor);
        myTable.addCell("Burger",tableBodyColor);
        myTable.addCell("80",tableBodyColor);
        myTable.addCell("4",tableBodyColor);
        myTable.addCell("320",tableBodyColor);

        myTable.addCell("3.",tableBodyColor);
        myTable.addCell("Biryani",tableBodyColor);
        myTable.addCell("140",tableBodyColor);
        myTable.addCell("1",tableBodyColor);
        myTable.addCell("140",tableBodyColor);

        myTable.addCell("",null);
        myTable.addCell("",null);
        myTable.addCell("Sub Total",null);
        myTable.addCell("",null);
        myTable.addCell("700",null);

        myTable.addCell("",null);
        myTable.addCell("",null);
        myTable.addCell("GST",null);
        myTable.addCell("10%",null);
        myTable.addCell("70",null);

        myTable.addCell("",null);
        myTable.addCell("",null);
        myTable.addCell("Grand Total",tableHeadColor);
        myTable.addCell("",tableHeadColor);
        myTable.addCell("770",tableHeadColor);

        //Methods of payment
        String[] paymentMethod={"Methods of payment we accept:","Cash,PhonePe,GPay,RuPay,Maestro,","Visa,Mastercard and American Express"};
        myTextClass.addMultiLineText(paymentMethod,15,25,180,italicFont,10,new Color(122,122,122));

        //authorise signatory
        contentStream.setStrokingColor(Color.BLACK);//NonStroking
        contentStream.setLineWidth(2);
        contentStream.moveTo(pageWidth-250,150);
        contentStream.lineTo(pageWidth-25,150);
        contentStream.stroke();

        String authoSign="Authorised Signatory";
        float authoSignWidth=myTextClass.getTextWidth(authoSign,italicFont,16);
        int xpos=pageWidth-250+pageWidth-25;
        myTextClass.addSingleLineText(authoSign,(int)(xpos-authoSignWidth)/2,125,italicFont,16,Color.BLACK);//center position

        String bottomLine="Rain or shine, it's time to done";
        float bottomLineWidth=myTextClass.getTextWidth(bottomLine,font,20);
        myTextClass.addSingleLineText(bottomLine,(int)(pageWidth-bottomLineWidth)/2,50,italicFont,20,Color.DARK_GRAY);

        Color bottomRectColor=new Color(255,91,0);
        contentStream.setNonStrokingColor(bottomRectColor);
        contentStream.addRect(0,0,pageWidth,30);
        contentStream.fill();


        contentStream.close();
        document.save("D:\\PDFBoxTesting\\src\\main\\resources\\img\\myPDF.pdf");
        document.close();
        System.out.println("PDF created");

    }
    public static class MyTextClass {

        PDDocument document;
        PDPageContentStream contentStream;

        public MyTextClass(PDDocument document,PDPageContentStream contentStream){
            this.document=document;
            this.contentStream=contentStream;
        }
        void addSingleLineText(String text, int xPosition, int yPosition, PDFont font, float fontSize, Color color) throws IOException{
            contentStream.beginText();
            contentStream.setFont(font,fontSize);
            contentStream.setNonStrokingColor(color);
            contentStream.newLineAtOffset(xPosition,yPosition);
            contentStream.showText(text);
            contentStream.endText();
            contentStream.moveTo(0,0);
        }

        void  addMultiLineText(String[] textArray,float leading,int xPosition,int yPosition,PDFont font,float fontSize,Color color) throws IOException {
            contentStream.beginText();
            contentStream.setFont(font,fontSize);
            contentStream.setNonStrokingColor(color);
            contentStream.setLeading(leading);
            contentStream.newLineAtOffset(xPosition,yPosition);

            for(String text: textArray){
                contentStream.showText(text);
                contentStream.newLine();
            }

            contentStream.endText();
            contentStream.moveTo(0,0);
        }

        float getTextWidth(String text,PDFont font,float fontSize) throws IOException {
            return font.getStringWidth(text)/1000*fontSize;
        }

    }

    private static class MyTableClass{
        PDDocument document;
        PDPageContentStream contentStream;
        private int[] colWidths;
        private int cellHeight;
        private int xPosition;
        private int yPosition;
        private int colPosition;
        private int xInitialPosition=0;
        private float fontSize;
        private PDFont font;
        private Color fontColor;

        public MyTableClass(PDDocument document, PDPageContentStream contentStream) {
            this.document = document;
            this.contentStream = contentStream;
        }

        public void setTable(int[] colWidths, int cellHeight, int xPosition, int yPosition) {
            this.colWidths = colWidths;
            this.cellHeight = cellHeight;
            this.xPosition = xPosition;
            this.yPosition = yPosition;
            xInitialPosition=xPosition;
        }

        public void setTableFont(PDFont font,float fontSize, Color fontColor) {
            this.fontSize = fontSize;
            this.font = font;
            this.fontColor = fontColor;
        }

        public void addCell(String text,Color fillColor) throws IOException {
          contentStream.setStrokingColor(1f);//100% transparent

          if(fillColor!=null){
              contentStream.setNonStrokingColor(fillColor);
          }
          contentStream.addRect(xPosition,yPosition,colWidths[colPosition],cellHeight);

          if(fillColor==null)
              contentStream.stroke();
          else
              contentStream.fillAndStroke();

          contentStream.beginText();
          contentStream.setNonStrokingColor(fontColor);

          if(colPosition==4||colPosition==2){ //2 and 4 is right alignment and rest all left alignment
              float fontWidth=font.getStringWidth(text)/1000*fontSize;//error
              contentStream.newLineAtOffset(xPosition+colWidths[colPosition]-20-fontWidth,yPosition+10);
            }
          else {
              contentStream.newLineAtOffset(xPosition+20,yPosition+10);
          }

          contentStream.showText(text);
          contentStream.endText();

          xPosition=xPosition+colWidths[colPosition];
          colPosition++;

          if(colPosition==colWidths.length){
              colPosition=0;
              xPosition=xInitialPosition;
              yPosition-=cellHeight;
          }
        }


    }
}
