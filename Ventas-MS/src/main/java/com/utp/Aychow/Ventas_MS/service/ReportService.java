package com.utp.Aychow.Ventas_MS.service;


import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private final DataSource dataSource;

    public ReportService(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public byte[] exportToPdf(Long idUsuario, Long idVenta) throws JRException, IOException, SQLException {
        try (Connection connection = dataSource.getConnection()) {
            Map<String, Object> params = new HashMap<>();
            params.put("idUsuario", idUsuario);
            params.put("idVenta", idVenta);

            InputStream reportStream = new ClassPathResource("AyChowRecibo.jrxml").getInputStream();
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, connection);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        }
    }

}





