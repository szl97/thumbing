import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:thumbing/logic/bloc/user/login_bloc.dart';
import 'package:thumbing/presentation/pages/login/login_form.dart';

class LoginPage extends StatelessWidget {
  const LoginPage({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      appBar: AppBar(
        title: const Text('登录'),
        backgroundColor: Colors.black26,
      ),
      body: Padding(
        padding: EdgeInsets.all(12.0),
        child: BlocProvider(
          create: (context) => BlocProvider.of<LoginBloc>(context),
          child: LoginForm(),
        ),
      ),
    );
  }
}
