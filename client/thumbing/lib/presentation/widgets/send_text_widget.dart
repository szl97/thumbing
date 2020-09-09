import 'package:flutter/material.dart';
import 'package:thumbing/presentation/util/screen_utils.dart';

///文本发送框
class SendTextFieldWidget extends StatelessWidget {
  final TextEditingController controller;
  final FocusNode focusNode;
  final VoidCallback onSubmitted;
  final ValueChanged<String> onChanged;
  final VoidCallback onTab;
  final String hintText;
  final String prefixText;
  final EdgeInsetsGeometry margin;
  final bool autoFocus;

  SendTextFieldWidget(
      {Key key, this.controller,
      this.hintText, this.prefixText,
      this.onSubmitted, this.onChanged,
      this.onTab,
      this.margin,
      this.autoFocus, this.focusNode})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Row(
      children: <Widget>[
        Container(
          margin: margin == null ? EdgeInsets.all(0.0) : margin,
          width: MediaQuery.of(context).size.width - ScreenUtils.getInstance().getWidth(70),
          alignment: AlignmentDirectional.center,
          decoration: BoxDecoration(
              color: Color.fromARGB(255, 237, 236, 237),
              borderRadius: BorderRadius.circular(24.0)),
          child: TextField(
            controller: controller,
            focusNode: focusNode,
            autofocus: autoFocus,
            onTap: onTab,
            onChanged: onChanged,
            cursorColor: Colors.black,
            decoration: InputDecoration(
                contentPadding:
                EdgeInsets.only(left: 10, top: 5, bottom: 5, right: 10),
                hintText: hintText,
                hintStyle: TextStyle(fontSize: 12),
                prefixText: prefixText,
                prefixStyle: TextStyle(fontSize: 12,),
                isDense: true,
                border: const OutlineInputBorder(
                  gapPadding: 0,
                  borderSide: BorderSide(
                    width: 0,
                    style: BorderStyle.none,
                  ),
                ),hintMaxLines: 1),
            style: TextStyle(fontSize: 16),
            minLines: 1,
            maxLines: 5,
          ),
        ),
        Spacer(),
        Container(
            child: IconButton(
                icon: Icon(Icons.send, color: Colors.blueAccent),
                onPressed: onSubmitted)
        ),
      ],
    );
  }

  getContainer(BuildContext context, ValueChanged<String> onSubmitted) {
    return Container(
      width: MediaQuery.of(context).size.width,
      alignment: AlignmentDirectional.center,
      decoration: BoxDecoration(
          color: Color.fromARGB(255, 237, 236, 237),
          borderRadius: BorderRadius.circular(24.0)),
      child: Column(
        children: <Widget>[
          TextField(
            autofocus: autoFocus,
            onSubmitted: onSubmitted,
            cursorColor: Colors.black,
            decoration: InputDecoration(
                contentPadding:
                EdgeInsets.only(left: 10, top: 5, bottom: 5, right: 10),
                hintText: hintText,
                hintStyle: TextStyle(fontSize: 12),
                isDense: true,
                border: const OutlineInputBorder(
                  gapPadding: 0,
                  borderSide: BorderSide(
                    width: 0,
                    style: BorderStyle.none,
                  ),
                )),
            style: TextStyle(fontSize: 16),
            minLines: 1,
            maxLines: 5,
          ),
        ],
      )
    );
  }
}
