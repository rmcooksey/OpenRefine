package com.google.refine.offline;

import edu.mit.simile.butterfly.*;
import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;

/**
 * User: rmcooksey
 * Date: 06/03/13
 * Time: 12:15
 */
public class DummyButterflyModule implements ButterflyModule {
    public void init(ServletConfig servletConfig) throws Exception {
    }

    public void destroy() throws Exception {
    }

    public void initScope(Context context, Scriptable scriptable) {
    }

    public void setClassLoader(ClassLoader classLoader) {
    }

    public void setMounter(ButterflyMounter butterflyMounter) {
    }

    public void setName(String s) {
    }

    public void setPath(File file) {
    }

    public void setMountPoint(MountPoint mountPoint) {
    }

    public void setExtended(ButterflyModule butterflyModule) {
    }

    public void addExtendedBy(ButterflyModule butterflyModule) {
    }

    public void setImplementation(String s) {
    }

    public void setDependency(String s, ButterflyModule butterflyModule) {
    }

    public void setModules(Map<String, ButterflyModule> stringButterflyModuleMap) {
    }

    public void setScript(URL url, Script script) {
    }

    public void setScriptable(ButterflyScriptableObject butterflyScriptableObject) {
    }

    public void setTemplateEngine(VelocityEngine velocityEngine) {
    }

    public void setProperties(ExtendedProperties extendedProperties) {
    }

    public void setTimer(Timer timer) {
    }

    public ServletConfig getServletConfig() {
        return null;  
    }

    public ServletContext getServletContext() {
        return null;  
    }

    public String getName() {
        return "core";  
    }

    public ExtendedProperties getProperties() {
        return null;  
    }

    public File getPath() {
        return null;  
    }

    public MountPoint getMountPoint() {
        return null;  
    }

    public ButterflyMounter getMounter() {
        return null;  
    }

    public File getTemporaryDir() {
        return null;  
    }

    public ButterflyModule getModule(String s) {
        return null;  
    }

    public ButterflyModule getExtendedModule() {
        return null;  
    }

    public Map<String, ButterflyModule> getDependencies() {
        return null;  
    }

    public Set<String> getImplementations() {
        return null;  
    }

    public Set<ButterflyScriptableObject> getScriptables() {
        return null;  
    }

    public VelocityEngine getTemplateEngine() {
        return null;  
    }

    public URL getResource(String s) {
        return null;  
    }

    public String getRelativePath(HttpServletRequest httpServletRequest) {
        return null;  
    }

    public PrintWriter getFilteringWriter(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, boolean b) throws IOException {
        return null;  
    }

    public String getString(HttpServletRequest httpServletRequest) throws Exception {
        return null;  
    }

    public String getContextPath(HttpServletRequest httpServletRequest, boolean b) {
        return null;  
    }

    public boolean process(String s, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return false;  
    }

    public boolean redirect(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String s) throws Exception {
        return false;  
    }

    public boolean sendBinary(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String s, String s2) throws Exception {
        return false;  
    }

    public boolean sendBinary(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, URL url, String s) throws Exception {
        return false;  
    }

    public boolean sendText(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String s, String s2, String s3, boolean b) throws Exception {
        return false;  
    }

    public boolean sendText(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, URL url, String s, String s2, boolean b) throws Exception {
        return false;  
    }

    public boolean sendWrappedText(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, URL url, String s, String s2, String s3, String s4, boolean b) throws Exception {
        return false;  
    }

    public boolean sendTextFromTemplate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, VelocityContext velocityContext, String s, String s2, String s3, boolean b) throws Exception {
        return false;  
    }

    public boolean sendString(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String s, String s2, String s3) throws Exception {
        return false;  
    }

    public boolean sendError(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, int i, String s) throws Exception {
        return false;  
    }

    public List<ButterflyModuleImpl.Level> makePath(String s, Map<String, String> stringStringMap) {
        return null;  
    }
}
