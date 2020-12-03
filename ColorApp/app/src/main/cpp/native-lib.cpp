#include <jni.h>
#include <opencv2/opencv.hpp>


using namespace cv;




extern "C"
JNIEXPORT void JNICALL
Java_com_example_colorapp_ColoringActivity_imageprocessing(JNIEnv *env,
                                                                           jobject instance,
                                                                           jlong inputImage,
                                                                           jlong outputImage,
                                                                           jint th1,
                                                                           jint th2) {


    Mat &img_input = *(Mat *) inputImage;
    Mat &img_output = *(Mat *) outputImage;

    cvtColor( img_input, img_output, COLOR_RGB2GRAY);

    blur( img_output, img_output, Size(5,5) );
    Canny( img_output, img_output, th1, th2);
    threshold(img_output,img_output,0,255,THRESH_BINARY_INV);

}extern "C"
JNIEXPORT void JNICALL
Java_com_example_colorapp_ColoringActivity_1Camera_imageprocessing(JNIEnv *env,jobject instance,
                                                                   jlong inputImage,
                                                                   jlong outputImage, jint th1,
                                                                   jint th2) {
    Mat &img_input = *(Mat *) inputImage;
    Mat &img_output = *(Mat *) outputImage;

    cvtColor( img_input, img_output, COLOR_RGB2GRAY);

    blur( img_output, img_output, Size(5,5) );
    Canny( img_output, img_output, th1, th2);
    threshold(img_output,img_output,0,255,THRESH_BINARY_INV);
    // TODO: implement imageprocessing()
}