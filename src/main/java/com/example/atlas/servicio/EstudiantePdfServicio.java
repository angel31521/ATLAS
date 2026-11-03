package com.example.atlas.servicio;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.example.atlas.modelo.EstudianteModel;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;

@Service 
public class EstudiantePdfServicio {
 public byte[] generar(EstudianteModel e) {
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A4, 50, 50, 50, 50);

        Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Font fuenteSub = FontFactory.getFont(FontFactory.HELVETICA, 11);
        Font fuenteEtiqueta = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
        Font fuenteTexto = FontFactory.getFont(FontFactory.HELVETICA, 11);

        try {
            PdfWriter.getInstance(doc, salida);
            doc.open();

            doc.add(new Paragraph("Ficha del estudiante", fuenteTitulo));
            doc.add(new Paragraph(e.getCurso().getInstitucion().getNombre(), fuenteSub));

            Paragraph fecha = new Paragraph(
                    "Generado el " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    fuenteSub);
            fecha.setSpacingAfter(16);
            doc.add(fecha);

            PdfPTable tabla = new PdfPTable(new float[] { 1.3f, 3f });
            tabla.setWidthPercentage(100);

            fila(tabla, "Nombre", e.getNombre(), fuenteEtiqueta, fuenteTexto);
            fila(tabla, "Curso", e.getCurso().getGrado() + " - Grupo " + e.getCurso().getGrupo(),
                    fuenteEtiqueta, fuenteTexto);
            fila(tabla, "Dirección", e.getDireccion(), fuenteEtiqueta, fuenteTexto);
            fila(tabla, "EPS afiliada", e.getEps_afiliada(), fuenteEtiqueta, fuenteTexto);
            fila(tabla, "Madre", e.getNombre_madre(), fuenteEtiqueta, fuenteTexto);
            fila(tabla, "Teléfono madre", e.getTelefono_madre(), fuenteEtiqueta, fuenteTexto);
            fila(tabla, "Padre", e.getNombre_padre(), fuenteEtiqueta, fuenteTexto);
            fila(tabla, "Teléfono padre", e.getTelefono_padre(), fuenteEtiqueta, fuenteTexto);

            doc.add(tabla);
            doc.close();   // importante: cerrar ANTES de leer los bytes

        } catch (DocumentException ex) {
            throw new IllegalStateException("No se pudo generar el PDF.", ex);
        }

        return salida.toByteArray();
    }

    private void fila(PdfPTable tabla, String etiqueta, String valor, Font fEtiqueta, Font fTexto) {
        PdfPCell celdaEtiqueta = new PdfPCell(new Phrase(etiqueta, fEtiqueta));
        PdfPCell celdaValor = new PdfPCell(new Phrase(
                (valor == null || valor.isBlank()) ? "-" : valor, fTexto));
        celdaEtiqueta.setPadding(8);
        celdaValor.setPadding(8);
        tabla.addCell(celdaEtiqueta);
        tabla.addCell(celdaValor);
    }
}
