from gtts import gTTS
import os
os.environ["SDL_AUDIODRIVER"]= "pulseaudio"
import pygame


class TTS:

    def add_tts(self, text) :
        tts = gTTS(text, lang="ko")
        tts_file = "tmp.mp3"
        tts.save(tts_file)
        return tts_file

    def play_tts(self, text) :
        tts_file = self.add_tts(text)

        pygame.mixer.init()
        pygame.mixer.music.load(tts_file)
        pygame.mixer.music.play()

        # 재생이 끝날 때까지 대기
        while pygame.mixer.music.get_busy():
            pygame.time.Clock().tick(10)

        os.remove(tts_file)

    def play_tts_file(self, sound_file) :
        pygame.mixer.init()
        pygame.mixer.music.load(sound_file)
        pygame.mixer.music.play()

        # 재생이 끝날 때까지 대기
        while pygame.mixer.music.get_busy():
            pygame.time.Clock().tick(10)
