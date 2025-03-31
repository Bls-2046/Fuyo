package com.github.fuyo.view.navigation.deepseek;

import javax.swing.*;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

public class WebRenderView extends JLayeredPane {

    private JFXPanel jfxPanel;
    private WebView webView;

    public WebRenderView() {
        initBrowser();
    }

    private void initBrowser() {

        setBounds(260,0,1100,768);

        // Fu*kin just exit plzzz
        Platform.setImplicitExit(false);
        jfxPanel = new JFXPanel();
        add(jfxPanel);
        jfxPanel.setBounds(0, 0, 1100, 768);

        SwingUtilities.invokeLater(() -> {
            Platform.runLater(() -> {
                webView = new WebView();
                jfxPanel.setScene(new Scene(webView));
            });
        });

        setVisible(true);
    }

    /**
     * LoadURL
     * @param url websiteURL
     */
    public void loadURL(final String url) {
        Platform.runLater(() -> {
            if (webView != null) {
                webView.getEngine().load(url);
            }
        });
    }

    /**
     * JavaScript Codes (用得上吗?)
     * @param script JavaScript代码
     */
    public void executeJavaScript(final String script) {
        Platform.runLater(() -> {
            if (webView != null) {
                webView.getEngine().executeScript(script);
            }
        });
    }

    /**
     * GC()
     */
    public void dispose() {
        if (jfxPanel != null) {
            Platform.runLater(() -> {
                if (webView != null) {
                    webView.getEngine().load(null);
                    webView = null;
                }
            });
            remove(jfxPanel);
            jfxPanel = null;
        }
    }

    @Override
    public void removeNotify() {
        dispose();
        super.removeNotify();
    }

}
