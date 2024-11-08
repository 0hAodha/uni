
'use strict'

// class constructor
function orientationInterpolator(data) {
    this.startTime = Date.now(); // milliseconds since Jan 1st 1970
    this.data = data;
    this.keyframe1 = 0;
    this.keyframe2 = 1;
}

orientationInterpolator.prototype.getEulerAngles = function() {
    var now = Date.now();
    var elapsed = now - this.startTime;
    while (elapsed >= this.data.times[this.keyframe2]) {
        this.keyframe1++;
        this.keyframe2++;
        if (this.keyframe2 >= this.data.times.length) { // loop back to keyframe 0
            var timeSinceLastFrame = elapsed - this.data.times[this.keyframe1];
            this.startTime = now - timeSinceLastFrame;
            this.keyframe1 = 0;
            this.keyframe2 = 1;
            elapsed = now - this.startTime;
        }
    }

    var timeBetweenKeyFrames = this.data.times[this.keyframe2] - this.data.times[this.keyframe1];
    var timeSinceKeyframe1 = elapsed - this.data.times[this.keyframe1];
    var fractionBetweenFrames = timeSinceKeyframe1 / timeBetweenKeyFrames;
    var frame1rot = this.data.angles[this.keyframe1];
    var frame2rot = this.data.angles[this.keyframe2];

    var rot = {
        x: frame1rot.x * (1 - fractionBetweenFrames) + frame2rot.x * fractionBetweenFrames,
        y: frame1rot.y * (1 - fractionBetweenFrames) + frame2rot.y * fractionBetweenFrames,
        z: frame1rot.z * (1 - fractionBetweenFrames) + frame2rot.z * fractionBetweenFrames
    };

    return rot;
};

