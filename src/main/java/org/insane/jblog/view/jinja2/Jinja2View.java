package org.insane.jblog.view.jinja2;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.AbstractTemplateView;

import com.hubspot.jinjava.Jinjava;

/**
 * View using the Jinja2 template engine.
 *
 * <p>
 * Exposes the following JavaBean properties:
 * <ul>
 * <li><b>url</b>: the location of the FreeMarker template to be wrapped,
 * relative to the FreeMarker template context (directory).
 * </ul>
 *
 * @author Artem Osadchyi
 * @since 30.07.2015
 * @see #setUrl
 * @see #setExposeSpringMacroHelpers
 */
public class Jinja2View extends AbstractTemplateView {
    private Jinjava jinja2Engine = new Jinjava();

    /**
     * Process the model map by merging it with the Jinja2 template. Output is
     * directed to the servlet response.
     * <p>
     * This method can be overridden if custom behavior is needed.
     */
    @Override
    protected void renderMergedTemplateModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        exposeHelpers(model, request);
        doRender(model, request, response);
    }

    /**
     * Expose helpers unique to each rendering operation. This is necessary so
     * that different rendering operations can't overwrite each other's formats
     * etc.
     * <p>
     * Called by {@code renderMergedTemplateModel}. The default implementation
     * is empty. This method can be overridden to add custom helpers to the
     * model.
     *
     * @param model
     *            The model that will be passed to the template at merge time
     * @param request
     *            current HTTP request
     * @throws Exception
     *             if there's a fatal error while we're adding information to
     *             the context
     * @see #renderMergedTemplateModel
     */
    protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) throws Exception {
    }

    /**
     * Render the Jinja2 view to the given response, using the given model map
     * which contains the complete template model to use.
     * <p>
     * The default implementation renders the template specified by the "url"
     * bean property, retrieved via {@code getTemplate}. It delegates to the
     * {@code processTemplate} method to merge the template instance with the
     * given template model.
     * <p>
     * Can be overridden to customize the behavior, for example to render
     * multiple templates into a single view.
     *
     * @param model
     *            the model to use for rendering
     * @param request
     *            current HTTP request
     * @param response
     *            current servlet response
     * @throws IOException
     *             if the template file could not be retrieved
     * @throws Exception
     *             if rendering failed
     * @see #setUrl
     * @see org.springframework.web.servlet.support.RequestContextUtils#getLocale
     * @see #getTemplate(java.util.Locale)
     * @see #processTemplate
     */
    protected void doRender(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (logger.isDebugEnabled())
            logger.debug("Rendering Jinja2 template [" + getUrl() + "] in Jinja2View '" + getBeanName() + "'");

        // Grab the locale-specific version of the template.
        Locale locale = RequestContextUtils.getLocale(request);
        processTemplate(getTemplate(locale), model, response);
    }

    /**
     * Retrieve the Jinja2 template for the given locale, to be rendering by
     * this view.
     * <p>
     * By default, the template specified by the "url" bean property will be
     * retrieved.
     *
     * @param locale
     *            the current locale
     * @return the Jinja2 template to render
     * @throws IOException
     *             if the template file could not be retrieved
     * @see #setUrl
     * @see #getTemplate(String, java.util.Locale)
     */
    protected String getTemplate(Locale locale) throws IOException {
        return getTemplate(getUrl(), locale);
    }

    /**
     * Retrieve the Jinja2 template specified by the given name.
     * <p>
     * Can be called by subclasses to retrieve a specific template, for example
     * to render multiple templates into a single view.
     *
     * @param name
     *            the file name of the desired template
     * @param locale
     *            the current locale
     * @return the Jinja2 template
     * @throws IOException
     *             if the template file could not be retrieved
     */
    protected String getTemplate(String name, Locale locale) throws IOException {
        ApplicationContext context = getApplicationContext();
        Resource resource = context.getResource(name);

        return IOUtils.toString(resource.getInputStream());
    }

    /**
     * Process the Jinja2 template to the servlet response.
     * <p>
     * Can be overridden to customize the behavior.
     *
     * @param template
     *            the template to process
     * @param model
     *            the model for the template
     * @param response
     *            servlet response (use this to get the OutputStream or Writer)
     * @throws IOException
     *             if the template file could not be retrieved
     */
    protected void processTemplate(String template, Map<String, Object> model, HttpServletResponse response) throws IOException {
        PrintWriter output = response.getWriter();

        output.write(jinja2Engine.render(template, model));

        output.flush();
    }

}
