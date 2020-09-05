import 'package:flutter/material.dart';
import 'package:thumbing/presentation/util/screen_utils.dart';

class BottomLoader extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      alignment: Alignment.center,
      child: Center(
        child: SizedBox(
          width: ScreenUtils.getInstance().getHeight(30),
          height: ScreenUtils.getInstance().getHeight(30),
          child: CircularProgressIndicator(
            strokeWidth: 1.5,
          ),
        ),
      ),
    );
  }
}
