package com.diplomski.backend.pdf;

import com.diplomski.backend.domain.Ticket;
import com.diplomski.backend.domain.enumeration.SeatClass;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.DashedBorder;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

@Component
@Data
public class GeneratorPDF {
    //private final Ticket ticket;
    private Ticket ticket;
    private String ticketName;



    public void createTicketPdf() throws FileNotFoundException, MalformedURLException {
        String ticketName=ticket.getBooking().getCustomer().getFirstName().toLowerCase()+"_"+ticket.getBooking().getCustomer().getLastName()+"_"+ticket.getCode();
        String path="C:\\Users\\Mihajlo.DESKTOP-T538ONP\\Desktop\\Diplomski\\backend-v1\\backend\\src\\main\\java\\com\\diplomski\\backend\\pdf\\"+ticketName+".pdf";
        PdfWriter pdfWriter=new PdfWriter(path);
        PdfDocument pdfDocument=new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        Document document=new Document(pdfDocument);
        String imagePath="C:\\Users\\Mihajlo.DESKTOP-T538ONP\\Desktop\\Diplomski\\backend-v1\\backend\\src\\main\\java\\com\\diplomski\\backend\\barcode_1.jpg";
        ImageData imageData= ImageDataFactory.create(imagePath);
        Image image=new Image(imageData);
        // HEADER FOR TICKET //
        float threecol=190f;
        float twocol=285f;
        float twocol150=twocol+150f;
        float twocolumn[]={twocol150,twocol};
        float fullwidth[]={threecol*3};
        float onecolumn[]={twocol150};
        float threeColumnWidth[]={threecol,threecol,threecol};

        Table table=new Table(threeColumnWidth);
        table.addCell(new Cell().add("Ticket").setFontSize(22f).setBorder(Border.NO_BORDER).setBold().setMarginRight(0.5f));
        table.addCell(new Cell().add(image.setAutoScale(true)).setMarginRight(4f).setBorder(Border.NO_BORDER));
        Table nestedTable=new Table(new float[]{twocol/2,twocol/2});
        nestedTable.addCell(new Cell().add("ticket code: ").setBold().setBorder(Border.NO_BORDER));
        nestedTable.addCell(new Cell().add(ticket.getCode()).setBorder(Border.NO_BORDER));
        nestedTable.addCell(new Cell().add("Creation date: ").setBold().setBorder(Border.NO_BORDER));
        nestedTable.addCell(new Cell().add(ticket.getBooking().getCreationTime().toString()).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(nestedTable).setBorder(Border.NO_BORDER).setMarginLeft(4f));

        Border border=new SolidBorder(Color.GRAY,2f);
        Table divider=new Table(fullwidth);
        divider.setBorder(border);
        document.add(table);
        document.add(new Paragraph());
        document.add(divider);
        /////////////////////////////////////////////
        // BODY OF TICKET //
        document.add(new Paragraph());

        Table headingBodyTable=new Table(twocolumn);
        headingBodyTable.addCell(firstBodyHeading("Customer information"));
        headingBodyTable.addCell(firstBodyHeading("Seat Information"));
        document.add(headingBodyTable.setMarginBottom(12f));

        Table centralBodyTable=new Table(twocolumn);
        centralBodyTable.addCell(cell10Left("First name",true));
        centralBodyTable.addCell(cell10Left("Code",true));
        centralBodyTable.addCell(cell10Left(ticket.getBooking().getCustomer().getFirstName(),false));
        centralBodyTable.addCell(cell10Left(ticket.getSeatStatus().getSeat().getCode(),false));
        centralBodyTable.addCell(cell10Left("Last name",true));
        centralBodyTable.addCell(cell10Left("Cabin",true));
        centralBodyTable.addCell(cell10Left(ticket.getBooking().getCustomer().getLastName(),false));
        centralBodyTable.addCell(cell10Left(ticket.getSeatStatus().getSeat().getCabin(),false));
        centralBodyTable.addCell(cell10Left("Email",true));
        centralBodyTable.addCell(cell10Left("Seat class",true));
        centralBodyTable.addCell(cell10Left(ticket.getBooking().getCustomer().getEmail(),false));
        centralBodyTable.addCell(cell10Left(ticket.getSeatStatus().getSeat().getSeatClass().toString(),false));
        document.add(centralBodyTable);
        Table centralBodyTableSingle1=new Table(onecolumn);
        centralBodyTableSingle1.addCell(cell10Left("Phone",true));
        centralBodyTableSingle1.addCell(cell10Left(ticket.getBooking().getCustomer().getPhone(),false));
        document.add(centralBodyTableSingle1);
        document.add(new Paragraph());
        Table divider2=new Table(fullwidth);
        Border dgb=new DashedBorder(Color.GRAY,0.5f);
        document.add(divider2.setBorder(dgb).setMarginBottom(10f));

        Table headingBodyFlightTable=new Table(onecolumn);
        headingBodyFlightTable.addCell(firstBodyHeading("Flight").setFontSize(14f));
        document.add(headingBodyFlightTable.setMarginBottom(12f));

        Table bodyFlightTable1=new Table(onecolumn);
        bodyFlightTable1.addCell(cell10Left("Flight number",true));
        bodyFlightTable1.addCell(cell10Left(ticket.getBooking().getFlight().getRoute().getIataFlight(),false));
        bodyFlightTable1.addCell(cell10Left("Flight date",true));
        bodyFlightTable1.addCell(cell10Left(ticket.getBooking().getFlight().getFlightDate().toString(),false));
        document.add(bodyFlightTable1.setMarginBottom(8f));

        Table bodyFlightTable2=new Table(threeColumnWidth);
        bodyFlightTable2.setBackgroundColor(Color.BLUE,0.7f);

        bodyFlightTable2.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
        bodyFlightTable2.addCell(new Cell().add("Departure").setFontColor(Color.WHITE).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        bodyFlightTable2.addCell(new Cell().add("Arrival").setFontColor(Color.WHITE).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER).setMarginRight(15f));
        document.add(bodyFlightTable2.setMarginBottom(4f));

        Table bodyFlightTable3=new Table(threeColumnWidth);
        bodyFlightTable3.addCell(new Cell().add("Airport").setFontColor(Color.BLACK).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
        bodyFlightTable3.addCell(new Cell().add(ticket.getBooking().getFlight().getRoute().getDepartureAirport().getName()).setFontColor(Color.DARK_GRAY).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        bodyFlightTable3.addCell(new Cell().add(ticket.getBooking().getFlight().getRoute().getArrivalAirport().getName()).setFontColor(Color.DARK_GRAY).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER).setMarginRight(15f));

        bodyFlightTable3.addCell(new Cell().add("Time").setFontColor(Color.BLACK).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
        bodyFlightTable3.addCell(new Cell().add(ticket.getBooking().getFlight().getDepScheduled().toString()).setFontColor(Color.DARK_GRAY).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        bodyFlightTable3.addCell(new Cell().add(ticket.getBooking().getFlight().getArrScheduled().toString()).setFontColor(Color.DARK_GRAY).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER).setMarginRight(15f));

        bodyFlightTable3.addCell(new Cell().add("Terminal").setFontColor(Color.BLACK).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
        bodyFlightTable3.addCell(new Cell().add(ticket.getBooking().getFlight().getRoute().getDepartureTerminal()!=null?ticket.getBooking().getFlight().getRoute().getDepartureTerminal():"/").setFontColor(Color.DARK_GRAY).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        bodyFlightTable3.addCell(new Cell().add(ticket.getBooking().getFlight().getRoute().getArrivalTerminal()!=null?ticket.getBooking().getFlight().getRoute().getArrivalTerminal():"/").setFontColor(Color.DARK_GRAY).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER).setMarginRight(15f));
        document.add(bodyFlightTable3.setMarginBottom(20f));

        // Border dgb=new DashedBorder(Color.GRAY,0.5f);
        document.add(divider2.setBorder(dgb).setMarginBottom(10f));

        Table bodyFlightTable4=new Table(onecolumn);
        bodyFlightTable4.addCell(new Cell().add("Amount").setBorder(Border.NO_BORDER).setFontSize(16f).setBold().setTextAlignment(TextAlignment.RIGHT));
        bodyFlightTable4.addCell(new Cell().add(ticket.getAmount().toString()).setBorder(Border.NO_BORDER).setFontSize(16f).setTextAlignment(TextAlignment.RIGHT));
        document.add(bodyFlightTable4).setBottomMargin(14f);
        document.add(new Paragraph());
        document.add(new Paragraph());
        document.add(new Paragraph());
        document.add(divider.setBorder(border));
        document.close();
    }
    private static Cell headerTextCell(String textValue){
        return new Cell().add(textValue).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT);
    }
    private static  Cell headerValueCell(String textValue){
        return new Cell().add(textValue).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }
    private static  Cell firstBodyHeading(String text){
        return new Cell().add(text).setFontSize(12f).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }
    private static Cell cell10Left(String textValue,Boolean isBold){
        Cell cell=new Cell().add(textValue).setFontSize(14f).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
        return isBold?cell.setBold():cell;
    }
    public String getFileName(){
       return ticketName=ticket.getBooking().getCustomer().getFirstName().toLowerCase()+"_"+ticket.getBooking().getCustomer().getLastName()+"_"+ticket.getCode();
    }
}
