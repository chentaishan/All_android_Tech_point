package com.example.annotaionprocessor;

import com.google.auto.service.AutoService;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class MyProcessor extends AbstractProcessor {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

//        Utility methods for operating on program elements.
        Elements elementUtils = processingEnv.getElementUtils();

        /**
         * * This interface supports the creation of new files by an annotation
         *  * processor.  Files created in this way will be known to the
         *  * annotation processing tool implementing this interface, better
         *  * enabling the tool to manage them.  Source and class files so
         *  * created will be {@linkplain RoundEnvironment#getRootElements
         *  * considered for processing} by the tool in a subsequent {@linkplain
         *  * RoundEnvironment round of processing} after the {@code close}
         *  * method has been called on the {@code Writer} or {@code
         *  * OutputStream} used to write the contents of the file.
         */
        Filer filer = processingEnv.getFiler();


        Locale locale = processingEnv.getLocale();

//          * A {@code Messager} provides the way for an annotation processor to
        // * report error messages, warnings, and other notices.  Elements,
        // * annotations, and annotation values can be passed to provide a
        // * location hint for the message.  However, such location hints may be
        // * unavailable or only approximate.
        Messager messager = processingEnv.getMessager();

        Map<String, String> options = processingEnv.getOptions();
        SourceVersion sourceVersion = processingEnv.getSourceVersion();
        Types typeUtils = processingEnv.getTypeUtils();


        messager.printMessage(Diagnostic.Kind.NOTE,"hh");


    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        System.out.println(roundEnv);

        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(BindView.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

}