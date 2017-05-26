# Lame音频转换Mp3

## 原理 
用到开源的C库Lame<https://sourceforge.net/projects/lame/>

将Lame打包成so库文件。

	#include <jni.h>
	#include "lame.h"
	
	
	JNIEXPORT jstring JNICALL
	Java_com_jiangwei_lameexample_MainActivity_getLameVersionName(JNIEnv *env, jobject instance) {
	
	    jstring version = (*env)->NewStringUTF(env, get_lame_version());
	
	    return version;
	}
	
	// 将wav音频类型转换成mp3类型的文件格式
	JNIEXPORT void JNICALL
	Java_com_jiangwei_lameexample_MainActivity_converToMp3(JNIEnv *env, jobject instance, jstring wav_,
	                                                       jstring mp3_) {
	    const char *cwav = (*env)->GetStringUTFChars(env, wav_, 0);
	    const char *cmp3 = (*env)->GetStringUTFChars(env, mp3_, 0);
	
	    FILE *fwav = fopen(cwav, "rb");
	    FILE *fmp3 = fopen(cmp3, "wb");
	
	    short int wav_buffer[8192 * 2];
	    unsigned char mp3_buffer[8192];
	
	    lame_t lame = lame_init();
	
	    lame_set_in_samplerate(lame, 44100);
	
	    lame_set_num_channels(lame, 2);
	
	    lame_set_VBR(lame, vbr_default);
	
	    lame_init_params(lame);
	
	    int read;
	    int write;
	    int total = 0;
	    do {
	        read = fread(wav_buffer, sizeof(short int) * 2, 8192, fwav);
	        total += read * sizeof(short int) * 2;
	        if (read != 0) {
	            write = lame_encode_buffer_interleaved(lame, wav_buffer, read, mp3_buffer, 8192);
	            fwrite(mp3_buffer, sizeof(unsigned char), write, fmp3);
	        }
	        if (read == 0) {
	            lame_encode_flush(lame, mp3_buffer, 8192);
	        }
	
	    } while (read != 0);
	    lame_close(lame);
	    fclose(fwav);
	    fclose(fmp3);
	
	    (*env)->ReleaseStringUTFChars(env, wav_, cwav);
	    (*env)->ReleaseStringUTFChars(env, mp3_, cmp3);
	}